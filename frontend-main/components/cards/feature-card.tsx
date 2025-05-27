'use client';

import { Card, CardContent } from '@/components/ui/card';
import { UserPlus, LogIn, FileEdit, ClipboardList, Send } from 'lucide-react';

const iconMap = {
  UserPlus,
  LogIn,
  FileEdit,
  ClipboardList,
  Send,
};

interface FeatureCardProps {
  step: string;
  title: string;
  description: string;
  icon: keyof typeof iconMap;
}

export default function FeatureCard({ step, title, description, icon }: FeatureCardProps) {
  const Icon = iconMap[icon];
  
  return (
    <Card className="min-w-[260px] max-w-[300px] transition-all hover:shadow-md hover:-translate-y-1 duration-300">
      <CardContent className="p-6 flex flex-col items-center text-center">
        <div className="flex items-center justify-center mb-4">
          <div className="w-12 h-12 rounded-full bg-[#3FDBB9]/20 flex items-center justify-center">
            <Icon className="h-6 w-6 text-[#18b190]" />
          </div>
        </div>
        <div className="w-8 h-8 rounded-full bg-[#18b190] text-white flex items-center justify-center text-sm font-bold mb-4">
          {step}
        </div>
        <h3 className="font-semibold text-lg mb-2">{title}</h3>
        <p className="text-muted-foreground text-sm">{description}</p>
      </CardContent>
    </Card>
  );
}