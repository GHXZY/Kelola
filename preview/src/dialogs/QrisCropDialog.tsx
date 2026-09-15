import React, { useState, useRef, useEffect } from 'react';
import { X, Check, ZoomIn, ZoomOut, Move, RotateCcw, Crop } from 'lucide-react';

interface QrisCropDialogProps {
  imageSrc: string;
  onCropComplete: (croppedDataUrl: string) => void;
  onCancel: () => void;
}

export const QrisCropDialog: React.FC<QrisCropDialogProps> = ({
  imageSrc,
  onCropComplete,
  onCancel
}) => {
  const [zoom, setZoom] = useState<number>(1.2);
  const [panX, setPanX] = useState<number>(0);
  const [panY, setPanY] = useState<number>(0);
  const [imageLoaded, setImageLoaded] = useState<boolean>(false);
  const [imageDimensions, setImageDimensions] = useState<{ width: number; height: number }>({
    width: 0,
    height: 0
  });

  const imgRef = useRef<HTMLImageElement | null>(null);
  const previewCanvasRef = useRef<HTMLCanvasElement | null>(null);
  const isDraggingRef = useRef<boolean>(false);
  const lastMousePosRef = useRef<{ x: number; y: number }>({ x: 0, y: 0 });

  // Load image
  useEffect(() => {
    const img = new Image();
    img.crossOrigin = 'anonymous';
    img.src = imageSrc;
    img.onload = () => {
      imgRef.current = img;
      setImageDimensions({ width: img.naturalWidth, height: img.naturalHeight });
      setImageLoaded(true);
      // Auto center
      setPanX(0);
      setPanY(0);
      setZoom(1.0);
    };
  }, [imageSrc]);

  // Update live preview canvas
  useEffect(() => {
    if (!imageLoaded || !imgRef.current || !previewCanvasRef.current) return;

    const canvas = previewCanvasRef.current;
    const ctx = canvas.getContext('2d');
    if (!ctx) return;

    const img = imgRef.current;
    const canvasSize = canvas.width; // 280

    ctx.clearRect(0, 0, canvasSize, canvasSize);

    // Compute base scale so image fills the square crop box
    const baseScale = Math.max(canvasSize / img.naturalWidth, canvasSize / img.naturalHeight);
    const effectiveScale = baseScale * zoom;

    const drawWidth = img.naturalWidth * effectiveScale;
    const drawHeight = img.naturalHeight * effectiveScale;

    // Center point + pan offsets
    const drawX = (canvasSize - drawWidth) / 2 + panX;
    const drawY = (canvasSize - drawHeight) / 2 + panY;

    ctx.drawImage(img, drawX, drawY, drawWidth, drawHeight);
  }, [imageLoaded, zoom, panX, panY]);

  // Mouse / Touch drag handlers for panning
  const handleMouseDown = (e: React.MouseEvent) => {
    isDraggingRef.current = true;
    lastMousePosRef.current = { x: e.clientX, y: e.clientY };
  };

  const handleMouseMove = (e: React.MouseEvent) => {
    if (!isDraggingRef.current) return;
    const deltaX = e.clientX - lastMousePosRef.current.x;
    const deltaY = e.clientY - lastMousePosRef.current.y;
    lastMousePosRef.current = { x: e.clientX, y: e.clientY };
    setPanX((prev) => prev + deltaX);
    setPanY((prev) => prev + deltaY);
  };

  const handleMouseUp = () => {
    isDraggingRef.current = false;
  };

  // Touch support for mobile preview
  const handleTouchStart = (e: React.TouchEvent) => {
    if (e.touches.length === 1) {
      isDraggingRef.current = true;
      lastMousePosRef.current = { x: e.touches[0].clientX, y: e.touches[0].clientY };
    }
  };

  const handleTouchMove = (e: React.TouchEvent) => {
    if (!isDraggingRef.current || e.touches.length !== 1) return;
    const deltaX = e.touches[0].clientX - lastMousePosRef.current.x;
    const deltaY = e.touches[0].clientY - lastMousePosRef.current.y;
    lastMousePosRef.current = { x: e.touches[0].clientX, y: e.touches[0].clientY };
    setPanX((prev) => prev + deltaX);
    setPanY((prev) => prev + deltaY);
  };

  const handleTouchEnd = () => {
    isDraggingRef.current = false;
  };

  const handleReset = () => {
    setZoom(1.0);
    setPanX(0);
    setPanY(0);
  };

  const handleApplyCrop = () => {
    if (!imgRef.current) return;

    // Export high resolution 600x600 square
    const exportCanvas = document.createElement('canvas');
    const exportSize = 600;
    exportCanvas.width = exportSize;
    exportCanvas.height = exportSize;
    const ctx = exportCanvas.getContext('2d');
    if (!ctx) return;

    const img = imgRef.current;
    const baseScale = Math.max(exportSize / img.naturalWidth, exportSize / img.naturalHeight);
    const effectiveScale = baseScale * zoom;

    const drawWidth = img.naturalWidth * effectiveScale;
    const drawHeight = img.naturalHeight * effectiveScale;

    // Scale pan offset proportionally from preview canvas (280px) to export canvas (600px)
    const scaleFactor = exportSize / 280;
    const drawX = (exportSize - drawWidth) / 2 + panX * scaleFactor;
    const drawY = (exportSize - drawHeight) / 2 + panY * scaleFactor;

    ctx.fillStyle = '#FFFFFF';
    ctx.fillRect(0, 0, exportSize, exportSize);
    ctx.drawImage(img, drawX, drawY, drawWidth, drawHeight);

    const croppedDataUrl = exportCanvas.toDataURL('image/png', 0.92);
    onCropComplete(croppedDataUrl);
  };

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/75 backdrop-blur-xs animate-fadeIn">
      <div className="bg-white rounded-2xl w-full max-w-md shadow-2xl overflow-hidden flex flex-col animate-scaleUp">
        {/* Header */}
        <div className="p-4 border-b border-slate-100 flex items-center justify-between flex-shrink-0 bg-slate-50/70">
          <div className="flex items-center gap-2 text-slate-800">
            <div className="w-8 h-8 rounded-full bg-brand-primary/10 flex items-center justify-center text-brand-primary">
              <Crop className="w-4 h-4" />
            </div>
            <div>
              <h2 className="text-sm font-bold text-slate-800">Potong Gambar QRIS (Kotak 1:1)</h2>
              <p className="text-[11px] text-slate-500">Sesuaikan agar pas di bagian kode QR saja</p>
            </div>
          </div>
          <button
            onClick={onCancel}
            className="w-8 h-8 rounded-full bg-slate-100 flex items-center justify-center text-slate-500 hover:text-slate-800 transition-colors"
          >
            <X className="w-4 h-4" />
          </button>
        </div>

        {/* Viewport Area */}
        <div className="p-4 flex flex-col items-center select-none bg-slate-900/90 text-white">
          <div
            className="relative w-[280px] h-[280px] rounded-xl overflow-hidden border-2 border-brand-sky shadow-lg cursor-grab active:cursor-grabbing touch-none flex items-center justify-center bg-black"
            onMouseDown={handleMouseDown}
            onMouseMove={handleMouseMove}
            onMouseUp={handleMouseUp}
            onMouseLeave={handleMouseUp}
            onTouchStart={handleTouchStart}
            onTouchMove={handleTouchMove}
            onTouchEnd={handleTouchEnd}
          >
            <canvas
              ref={previewCanvasRef}
              width={280}
              height={280}
              className="w-full h-full block"
            />

            {/* Grid Lines (Rule of thirds) */}
            <div className="absolute inset-0 pointer-events-none grid grid-cols-3 grid-rows-3 border border-white/20">
              <div className="border-r border-b border-white/20" />
              <div className="border-r border-b border-white/20" />
              <div className="border-b border-white/20" />
              <div className="border-r border-b border-white/20" />
              <div className="border-r border-b border-white/20" />
              <div className="border-b border-white/20" />
              <div className="border-r border-b border-white/20" />
              <div className="border-r border-b border-white/20" />
              <div />
            </div>

            {/* Hint overlay */}
            <div className="absolute bottom-2 left-2 right-2 px-2 py-1 rounded bg-black/60 backdrop-blur-xs text-[10px] text-center text-slate-200 pointer-events-none flex items-center justify-center gap-1">
              <Move className="w-3 h-3 text-brand-sky" />
              <span>Geser gambar atau gunakan slider di bawah</span>
            </div>
          </div>

          <div className="text-[11px] text-slate-300 mt-2">
            Kotak di atas adalah tampilan persis yang akan muncul di layar kasir
          </div>
        </div>

        {/* Controls */}
        <div className="p-4 bg-white space-y-3.5 border-t border-slate-100">
          {/* Zoom Slider */}
          <div className="space-y-1">
            <div className="flex items-center justify-between text-xs font-semibold text-slate-700">
              <span className="flex items-center gap-1.5">
                <ZoomIn className="w-3.5 h-3.5 text-brand-primary" />
                <span>Zoom / Skala</span>
              </span>
              <span className="text-[11px] font-mono text-slate-500">{zoom.toFixed(1)}x</span>
            </div>
            <div className="flex items-center gap-2">
              <ZoomOut className="w-4 h-4 text-slate-400" />
              <input
                type="range"
                min="0.8"
                max="3.5"
                step="0.05"
                value={zoom}
                onChange={(e) => setZoom(parseFloat(e.target.value))}
                className="flex-1 h-2 bg-slate-200 rounded-lg appearance-none cursor-pointer accent-brand-primary"
              />
              <ZoomIn className="w-4 h-4 text-brand-primary" />
            </div>
          </div>

          {/* Quick Actions */}
          <div className="flex items-center justify-between pt-1">
            <button
              type="button"
              onClick={handleReset}
              className="h-8 px-2.5 rounded-input bg-slate-100 hover:bg-slate-200 text-slate-600 text-xs font-medium flex items-center gap-1.5 transition-all"
            >
              <RotateCcw className="w-3.5 h-3.5" />
              <span>Pusatkan Gambar</span>
            </button>

            <span className="text-[11px] text-slate-400 italic">
              {imageDimensions.width > 0 ? `${imageDimensions.width}x${imageDimensions.height} px` : ''}
            </span>
          </div>

          {/* Actions CTA */}
          <div className="pt-2 flex items-center gap-2 border-t border-slate-100">
            <button
              type="button"
              onClick={onCancel}
              className="flex-1 h-11 rounded-input border border-slate-200 text-slate-700 hover:bg-slate-50 text-xs font-semibold transition-all"
            >
              Batal
            </button>
            <button
              type="button"
              onClick={handleApplyCrop}
              className="flex-1 h-11 rounded-input bg-brand-primary hover:bg-brand-deep text-white text-xs font-semibold flex items-center justify-center gap-1.5 shadow-md shadow-brand-primary/20 transition-all"
            >
              <Check className="w-4 h-4" />
              <span>Terapkan Potongan</span>
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};
