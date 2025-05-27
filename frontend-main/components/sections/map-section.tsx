'use client';

import { useEffect, useRef } from 'react';
import 'leaflet/dist/leaflet.css';
import 'leaflet.locatecontrol/dist/L.Control.Locate.min.css'; // CSS do plugin
import { Skeleton } from '@/components/ui/skeleton';
import { useToast } from '@/hooks/use-toast';

type Point = { lat: number; lng: number; type: string; status: string };
const problemPoints: Point[] = [
  { lat: -23.9608, lng: -46.3336, type: 'Esgoto a céu aberto', status: 'pendente' },
  { lat: -23.9532, lng: -46.3352, type: 'Vazamento', status: 'resolvido' },
  { lat: -23.9645, lng: -46.3300, type: 'Entupimento', status: 'analise' },
  { lat: -23.9700, lng: -46.3380, type: 'Alagamento', status: 'pendente' },
];

export default function MapSection() {
  const mapRef = useRef<HTMLDivElement>(null);
  const mapInstance = useRef<any>(null);
  const { toast } = useToast();

  useEffect(() => {
    let isMounted = true;

    const initMap = async () => {
      try {
        const L = (await import('leaflet')).default;
        // importa o plugin de localização
        await import('leaflet.locatecontrol');

        if (!mapRef.current || !isMounted) return;

        // inicializa o mapa
        const map = L.map(mapRef.current).setView([-23.9608, -46.3336], 13);
        mapInstance.current = map;

        // tile layer
        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
          attribution: '© OpenStreetMap contributors'
        }).addTo(map);

        // função de cor do marcador
        const getMarkerColor = (status: string) => {
          switch (status) {
            case 'resolvido': return 'green';
            case 'analise':   return 'blue';
            case 'pendente':  return 'red';
            default:          return 'gray';
          }
        };

        // adiciona marcadores
        problemPoints.forEach(point => {
          const color = getMarkerColor(point.status);
          const iconHtml = `
            <div
              style="
                background-color: ${color};
                width: 32px; height: 32px;
                border-radius: 50%;
                border: 2px solid white;
                box-shadow: 0 2px 4px rgba(0,0,0,0.3);
                display: flex;
                align-items: center;
                justify-content: center;
                color: white;
                font-size: 14px;
              "
            ></div>`;
          const markerIcon = L.divIcon({
            html: iconHtml,
            className: '',
            iconSize: [32, 32],
            iconAnchor: [16, 16],
          });

          const marker = L.marker([point.lat, point.lng], { icon: markerIcon }).addTo(map);
          marker.bindPopup(`
            <div class="p-2">
              <h3 class="font-bold">${point.type}</h3>
              <p class="text-sm">Status: ${point.status.charAt(0).toUpperCase() + point.status.slice(1)}</p>
              <button class="mt-2 px-2 py-1 bg-[#18b190] text-white rounded text-xs">Ver detalhes</button>
            </div>
          `);
        });

        // controle de localização
        ;(L.control as any).locate({
          position: 'topright',
          strings: { title: 'Mostrar minha localização' },
          flyTo: true,
          showCompass: true,
          locateOptions: { enableHighAccuracy: true }
        }).addTo(map);

      } catch (err) {
        console.error('Error loading map:', err);
        toast({
          title: "Erro ao carregar o mapa",
          description: "Não foi possível inicializar o Leaflet.",
          variant: "destructive",
        });
      }
    };

    initMap();

    return () => {
      isMounted = false;
      if (mapInstance.current) {
        mapInstance.current.remove();
        mapInstance.current = null;
      }
    };
  }, [toast]);

  return (
    <div className="relative">
      <div
        ref={mapRef}
        id="mapaSantos"
        className="w-full h-[500px] rounded-xl bg-gray-100"
      />
      {!mapInstance.current && (
        <div className="absolute inset-0 flex items-center justify-center">
          <div className="space-y-2">
            <Skeleton className="h-4 w-[100px]" />
            <Skeleton className="h-4 w-[200px]" />
          </div>
        </div>
      )}
    </div>
  );
}
