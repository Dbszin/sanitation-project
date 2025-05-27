'use client';

import { useState } from 'react';
import Link from 'next/link';
import { usePathname } from 'next/navigation';
import { Button } from '@/components/ui/button';
import {
  Sheet,
  SheetContent,
  SheetTrigger,
  SheetClose
} from '@/components/ui/sheet';
import { ModeToggle } from '@/components/mode-toggle';
import { Menu, LogIn, MapPin, Home, FileText, User } from 'lucide-react';
import { cn } from '@/lib/utils';

export default function Navbar() {
  const [isOpen, setIsOpen] = useState(false);
  const pathname = usePathname();

  const navItems = [
    { href: '/', label: 'Início', icon: Home },
    { href: '/relatar', label: 'Relatar', icon: FileText },
    { href: '/#mapaSantos', label: 'Mapa', icon: MapPin },
  ];

  return (
    <header className="sticky top-0 z-50 bg-background/95 backdrop-blur border-b">
      <div className="container mx-auto px-4 flex h-16 items-center justify-between">
        {/* Logo */}
        <Link href="/" className="flex items-center gap-2">
          <div className="h-8 w-8 rounded-full bg-[#3FDBB9] flex items-center justify-center">
            <span className="font-bold text-white">S+</span>
          </div>
          <span className="hidden sm:inline-block font-bold text-xl text-[#18b190]">
            Sanea+
          </span>
        </Link>

        {/* Menu Desktop */}
        <nav className="hidden md:flex flex-1 items-center justify-between ml-8">
          <ul className="flex items-center space-x-8">
            {navItems.map(({ href, label, icon: Icon }) => (
              <li key={href}>
                <Link
                  href={href}
                  className={cn(
                    'flex items-center text-sm font-medium transition-colors hover:text-[#18b190]',
                    pathname === href
                      ? 'text-[#18b190]'
                      : 'text-muted-foreground'
                  )}
                >
                  <Icon className="mr-2 h-4 w-4" />
                  {label}
                </Link>
              </li>
            ))}
          </ul>

          <div className="flex items-center space-x-3">
            <ModeToggle />

            <Link href="/login">
              <Button variant="ghost" size="sm" className="gap-1">
                <LogIn className="h-4 w-4" />
                <span>Entrar</span>
              </Button>
            </Link>

            <Link href="/login?tab=cadastro">
              <Button size="sm" className="bg-[#18b190] hover:bg-[#149d79] text-white">
                Cadastre-se
              </Button>
            </Link>
          </div>
        </nav>

        {/* Menu Mobile */}
        <div className="flex items-center md:hidden space-x-2">
          <ModeToggle />

          <Sheet open={isOpen} onOpenChange={setIsOpen}>
            <SheetTrigger asChild>
              <Button variant="ghost" size="icon">
                <Menu className="h-5 w-5" />
                <span className="sr-only">Abrir menu</span>
              </Button>
            </SheetTrigger>
            <SheetContent side="right" className="w-64">
              <div className="flex flex-col space-y-6 py-4">
                {/* Logo dentro do sheet */}
                <Link
                  href="/"
                  className="flex items-center gap-2"
                  onClick={() => setIsOpen(false)}
                >
                  <div className="h-8 w-8 rounded-full bg-[#3FDBB9] flex items-center justify-center">
                    <span className="font-bold text-white">S+</span>
                  </div>
                  <span className="font-bold text-xl text-[#18b190]">
                    Sanea+
                  </span>
                </Link>

                {/* Links */}
                <div className="flex flex-col space-y-3">
                  {navItems.map(({ href, label, icon: Icon }) => (
                    <SheetClose key={href} asChild>
                      <Link
                        href={href}
                        className={cn(
                          'flex items-center py-2 text-base font-medium transition-colors',
                          pathname === href
                            ? 'text-[#18b190]'
                            : 'text-muted-foreground'
                        )}
                      >
                        <Icon className="mr-2 h-5 w-5" />
                        {label}
                      </Link>
                    </SheetClose>
                  ))}
                </div>

                {/* Ações de login */}
                <div className="flex flex-col space-y-2">
                  <SheetClose asChild>
                    <Link href="/login">
                      <Button variant="outline" className="w-full gap-2">
                        <LogIn className="h-4 w-4" />
                        Entrar
                      </Button>
                    </Link>
                  </SheetClose>

                  <SheetClose asChild>
                    <Link href="/login?tab=cadastro">
                      <Button className="w-full bg-[#18b190] hover:bg-[#149d79]">
                        <User className="mr-2 h-4 w-4" />
                        Cadastre-se
                      </Button>
                    </Link>
                  </SheetClose>
                </div>
              </div>
            </SheetContent>
          </Sheet>
        </div>
      </div>
    </header>
  );
}
