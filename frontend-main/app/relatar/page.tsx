'use client';

import { useState } from 'react';
import { useRouter } from 'next/navigation';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import * as z from 'zod';
import { Button } from '@/components/ui/button';
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from '@/components/ui/form';
import { Input } from '@/components/ui/input';
import { Textarea } from '@/components/ui/textarea';
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { useToast } from '@/hooks/use-toast';
import { cadastrarRelato } from '@/lib/api';

const relatoSchema = z.object({
  tipo_problema: z.string().min(1, { message: 'Selecione o tipo de problema' }),
  descricao: z.string().min(10, { message: 'A descrição deve ter pelo menos 10 caracteres' }),
  data_ocorrido: z.string().min(1, { message: 'Data é obrigatória' }),
  cep: z.string().min(8, { message: 'CEP inválido' }),
  rua: z.string().min(1, { message: 'Rua é obrigatória' }),
  numero: z.string().min(1, { message: 'Número é obrigatório' }),
  bairro: z.string().min(1, { message: 'Bairro é obrigatório' }),
  cidade: z.string().min(1, { message: 'Cidade é obrigatória' }),
  estado: z.string().min(2, { message: 'Estado é obrigatório' }),
});

export default function RelatarPage() {
  const router = useRouter();
  const { toast } = useToast();
  
  const form = useForm<z.infer<typeof relatoSchema>>({
    resolver: zodResolver(relatoSchema),
    defaultValues: {
      tipo_problema: '',
      descricao: '',
      data_ocorrido: '',
      cep: '',
      rua: '',
      numero: '',
      bairro: '',
      cidade: '',
      estado: '',
    },
  });
  
  const onSubmit = async (values: z.infer<typeof relatoSchema>) => {
    try {
      await cadastrarRelato(values);
      toast({
        title: "Problema relatado com sucesso!",
        description: "Obrigado por contribuir com a melhoria da cidade.",
      });
      router.push('/perfil');
    } catch (error: any) {
      toast({
        title: "Erro ao relatar problema",
        description: error.response?.data?.error || "Tente novamente mais tarde.",
        variant: "destructive",
      });
    }
  };

  return (
    <div className="container mx-auto py-10 px-4">
      <Card className="max-w-2xl mx-auto">
        <CardHeader>
          <CardTitle>Relatar Problema</CardTitle>
          <CardDescription>
            Preencha os dados abaixo para relatar um problema de saneamento
          </CardDescription>
        </CardHeader>
        <CardContent>
          <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-4">
              <FormField
                control={form.control}
                name="tipo_problema"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Tipo de Problema</FormLabel>
                    <Select onValueChange={field.onChange} defaultValue={field.value}>
                      <FormControl>
                        <SelectTrigger>
                          <SelectValue placeholder="Selecione o tipo de problema" />
                        </SelectTrigger>
                      </FormControl>
                      <SelectContent>
                        <SelectItem value="vazamento">Vazamento de Água</SelectItem>
                        <SelectItem value="esgoto">Esgoto a Céu Aberto</SelectItem>
                        <SelectItem value="entupimento">Entupimento</SelectItem>
                        <SelectItem value="outro">Outro</SelectItem>
                      </SelectContent>
                    </Select>
                    <FormMessage />
                  </FormItem>
                )}
              />
              
              <FormField
                control={form.control}
                name="descricao"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Descrição</FormLabel>
                    <FormControl>
                      <Textarea
                        placeholder="Descreva o problema em detalhes"
                        className="resize-none"
                        {...field}
                      />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              
              <FormField
                control={form.control}
                name="data_ocorrido"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Data do Ocorrido</FormLabel>
                    <FormControl>
                      <Input type="date" {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              
              <div className="grid grid-cols-2 gap-4">
                <FormField
                  control={form.control}
                  name="cep"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>CEP</FormLabel>
                      <FormControl>
                        <Input placeholder="00000-000" {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                
                <FormField
                  control={form.control}
                  name="rua"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Rua</FormLabel>
                      <FormControl>
                        <Input placeholder="Nome da rua" {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
              </div>
              
              <div className="grid grid-cols-2 gap-4">
                <FormField
                  control={form.control}
                  name="numero"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Número</FormLabel>
                      <FormControl>
                        <Input placeholder="Número" {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                
                <FormField
                  control={form.control}
                  name="bairro"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Bairro</FormLabel>
                      <FormControl>
                        <Input placeholder="Nome do bairro" {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
              </div>
              
              <div className="grid grid-cols-2 gap-4">
                <FormField
                  control={form.control}
                  name="cidade"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Cidade</FormLabel>
                      <FormControl>
                        <Input placeholder="Nome da cidade" {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                
                <FormField
                  control={form.control}
                  name="estado"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Estado</FormLabel>
                      <FormControl>
                        <Input placeholder="UF" maxLength={2} {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
              </div>
              
              <div className="flex justify-between pt-4">
                <Button
                  type="button"
                  variant="outline"
                  onClick={() => router.push('/perfil')}
                >
                  Cancelar
                </Button>
                <Button type="submit" className="bg-[#18b190] hover:bg-[#149d79]">
                  Enviar Relato
                </Button>
              </div>
            </form>
          </Form>
        </CardContent>
      </Card>
    </div>
  );
}