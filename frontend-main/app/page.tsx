import Link from 'next/link';
import Image from 'next/image';
import { MapIcon, DropletIcon, ClipboardCheckIcon } from 'lucide-react';
import { Button } from '@/components/ui/button';
import MapSection from '@/components/sections/map-section';
import FeatureCard from '@/components/cards/feature-card';
import { ScrollArea, ScrollBar } from '@/components/ui/scroll-area';

export default function Home() {
  return (
    <div className="w-full">
      {/* Hero Section */}
      <section className="relative h-[600px] w-full overflow-hidden">
        <div className="absolute inset-0 bg-black/50 z-10"></div>
        <div 
          className="absolute inset-0 bg-cover bg-center"
          style={{ backgroundImage: "url(/assets/heroimage.png)" }}
        />
        <div className="relative z-20 container mx-auto h-full flex flex-col justify-center items-center text-center px-4">
          <h1 className="text-4xl md:text-5xl lg:text-6xl font-bold text-white mb-6 animate-fade-in">
            Bem-vindo ao <span className="text-[#3FDBB9]">Sanea+</span>
          </h1>
          <p className="text-lg md:text-xl text-white max-w-2xl mb-8 leading-relaxed">
            Uma plataforma dedicada a melhorar asa condições de saneamento básico da cidade de Santos-SP. 
            Relate problemas e ajude a cidade a se desenvolver!
          </p>
          <div className="flex flex-col sm:flex-row gap-4">
            <Button asChild size="lg" className="bg-[#18b190] hover:bg-[#149d79] text-white border-none">
              <Link href="/relatar">
                Relatar Problema
              </Link>
            </Button>
            <Button asChild variant="outline" size="lg" className="bg-white/10 text-white border-white/20 backdrop-blur-sm hover:bg-white/20">
              <Link href="#mapaSantos">
                Ver Mapa
              </Link>
            </Button>
          </div>
        </div>
      </section>
      
      {/* Info Section */}
      <section className="py-16 bg-white">
        <div className="container mx-auto px-4">
          <div className="grid grid-cols-1 md:grid-cols-2 gap-10 items-center">
            <div className="order-2 md:order-1">
              <h2 className="text-3xl font-bold text-[#18b190] mb-6">Você sabia?</h2>
              <p className="text-lg text-gray-700 leading-relaxed">
                Santos é destaque nacional em saneamento básico: 100% da população tem acesso à água potável, 
                99,93% à rede de esgoto e 97,60% de todo esgoto é tratado. Esses dados reforçam o compromisso 
                com a saúde pública e qualidade de vida.
              </p>
              <div className="mt-8 space-y-4">
                <div className="flex items-center gap-3">
                  <div className="w-10 h-10 rounded-full bg-[#3FDBB9]/20 flex items-center justify-center">
                    <DropletIcon className="h-5 w-5 text-[#18b190]" />
                  </div>
                  <p className="font-medium">100% de acesso à água potável</p>
                </div>
                <div className="flex items-center gap-3">
                  <div className="w-10 h-10 rounded-full bg-[#3FDBB9]/20 flex items-center justify-center">
                    <ClipboardCheckIcon className="h-5 w-5 text-[#18b190]" />
                  </div>
                  <p className="font-medium">99.93% de cobertura de rede de esgoto</p>
                </div>
                <div className="flex items-center gap-3">
                  <div className="w-10 h-10 rounded-full bg-[#3FDBB9]/20 flex items-center justify-center">
                    <MapIcon className="h-5 w-5 text-[#18b190]" />
                  </div>
                  <p className="font-medium">97.60% de tratamento de esgoto</p>
                </div>
              </div>
            </div>
            <div className="order-1 md:order-2 flex justify-center">
              <div className="relative w-full max-w-md h-[400px] rounded-2xl overflow-hidden shadow-lg transition-transform hover:scale-105 duration-300">
                <Image 
                  src="/assets/acidadecapa.jpg" 
                  alt="Saneamento em Santos" 
                  fill
                  className="object-cover"
                />
              </div>
            </div>
          </div>
        </div>
      </section>
      
      {/* How To Report Section */}
      <section className="py-16 bg-gray-50">
        <div className="container mx-auto px-4">
          <h2 className="text-3xl font-bold text-center text-[#18b190] mb-10">Como relatar um problema?</h2>
          
          <ScrollArea className="w-full overflow-x-auto">
            <div className="flex space-x-6 py-6">
              <FeatureCard 
                step="1"
                title="Crie uma conta"
                description="Faça um pequeno cadastro em nosso site para começar"
                icon="UserPlus"
              />
              <FeatureCard 
                step="2"
                title="Acesse sua conta"
                description="Entre com seu login e senha para acessar o sistema"
                icon="LogIn"
              />
              <FeatureCard 
                step="3"
                title="Clique em 'Relatar'"
                description="Use o botão para iniciar um novo relato de problema"
                icon="FileEdit"
              />
              <FeatureCard 
                step="4"
                title="Preencha o formulário"
                description="Informe todos os detalhes necessários sobre o problema"
                icon="ClipboardList"
              />
              <FeatureCard 
                step="5"
                title="Envie seu relato"
                description="Finalize o envio e acompanhe o status na sua conta"
                icon="Send"
              />
            </div>
            <ScrollBar orientation="horizontal" />
          </ScrollArea>
          
          <div className="mt-10 text-center">
            <Button asChild size="lg" className="bg-[#18b190] hover:bg-[#149d79] text-white">
              <Link href="/relatar">
                Comece a relatar agora
              </Link>
            </Button>
          </div>
        </div>
      </section>
      
      {/* Map Section */}
      <section id="mapaSantos" className="py-16 bg-white">
        <div className="container mx-auto px-4">
          <h2 className="text-3xl font-bold text-center text-[#18b190] mb-10">Mapa de Santos</h2>
          <p className="text-center text-gray-600 max-w-2xl mx-auto mb-10">
            Visualize a cidade de Santos e os pontos de interesse relacionados ao saneamento básico.
          </p>
          
          <div className="rounded-xl overflow-hidden shadow-lg border border-gray-100">
            <MapSection />
          </div>
        </div>
      </section>
    </div>
  );
}