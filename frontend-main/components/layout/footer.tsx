import Link from 'next/link';
import { Mail, Phone, MapPin, Instagram, Facebook, Twitter } from 'lucide-react';

export default function Footer() {
  return (
    <footer className="bg-[#18b190] text-white">
      <div className="container mx-auto py-12 px-4">
        <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
          <div>
            <h3 className="text-xl font-bold mb-4">Sanea+</h3>
            <p className="text-white/80 mb-4">
              Plataforma dedicada a melhorar as condições de saneamento básico da cidade de Santos-SP. 
              Sua participação é essencial para uma cidade mais limpa e saudável.
            </p>
            <div className="flex space-x-4">
              <Link href="#" className="text-white hover:text-white/80 transition-colors">
                <Instagram className="h-5 w-5" />
              </Link>
              <Link href="#" className="text-white hover:text-white/80 transition-colors">
                <Facebook className="h-5 w-5" />
              </Link>
              <Link href="#" className="text-white hover:text-white/80 transition-colors">
                <Twitter className="h-5 w-5" />
              </Link>
            </div>
          </div>
          
          <div>
            <h3 className="text-xl font-bold mb-4">Links Úteis</h3>
            <ul className="space-y-2">
              <li>
                <Link href="/" className="text-white/80 hover:text-white transition-colors">
                  Página Inicial
                </Link>
              </li>
              <li>
                <Link href="/relatar" className="text-white/80 hover:text-white transition-colors">
                  Relatar Problema
                </Link>
              </li>
              <li>
                <Link href="/#mapaSantos" className="text-white/80 hover:text-white transition-colors">
                  Mapa
                </Link>
              </li>
              <li>
                <Link href="/login" className="text-white/80 hover:text-white transition-colors">
                  Login / Cadastro
                </Link>
              </li>
              <li>
                <Link href="#" className="text-white/80 hover:text-white transition-colors">
                  Sobre Nós
                </Link>
              </li>
            </ul>
          </div>
          
          <div>
            <h3 className="text-xl font-bold mb-4">Contato</h3>
            <ul className="space-y-4">
              <li className="flex items-start">
                <MapPin className="h-5 w-5 mr-2 mt-0.5 flex-shrink-0" />
                <span className="text-white/80">
                  Santos, São Paulo, Brasil
                </span>
              </li>
              <li className="flex items-center">
                <Mail className="h-5 w-5 mr-2 flex-shrink-0" />
                <Link href="mailto:contato@saneamais.com" className="text-white/80 hover:text-white transition-colors">
                  contato@saneamais.com
                </Link>
              </li>
              <li className="flex items-center">
                <Phone className="h-5 w-5 mr-2 flex-shrink-0" />
                <Link href="tel:+551333333333" className="text-white/80 hover:text-white transition-colors">
                  (13) 3333-3333
                </Link>
              </li>
            </ul>
          </div>
        </div>
        
        <div className="border-t border-white/20 mt-12 pt-6 text-center text-white/60 text-sm">
          <p>&copy; {new Date().getFullYear()} Sanea+. Todos os direitos reservados.</p>
        </div>
      </div>
    </footer>
  );
}