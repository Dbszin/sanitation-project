'use client';

import { useState, useEffect } from 'react';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import { Avatar, AvatarFallback, AvatarImage } from '@/components/ui/avatar';
import { Badge } from '@/components/ui/badge';
import { 
  User, MapPin, FileText, Bell, Settings, PlusCircle, Check, AlertTriangle 
} from 'lucide-react';
import { Dialog, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle, DialogTrigger } from '@/components/ui/dialog';
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from '@/components/ui/form';
import { Input } from '@/components/ui/input';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import * as z from 'zod';
import { useToast } from '@/hooks/use-toast';
import Link from 'next/link';

const formSchema = z.object({
  nome: z.string().min(3, { message: 'O nome deve ter pelo menos 3 caracteres' }),
  dataNascimento: z.string(),
  endereco: z.string(),
  email: z.string().email({ message: 'Email inválido' }),
  telefone: z.string().min(10, { message: 'Telefone inválido' }),
  senhaAtual: z.string(),
  novaSenha: z.string(),
  repetirNovaSenha: z.string(),
});

interface RelatoProps {
  id: string;
  tipo: string;
  descricao: string;
  data: string;
  local: string;
  status: 'pendente' | 'analise' | 'resolvido' | 'cancelado';
}

export default function PerfilPage() {
  const [editOpen, setEditOpen] = useState(false);
  const { toast } = useToast();
  const [userName, setUserName] = useState('');
  
  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      nome: 'João Silva',
      dataNascimento: '1990-05-12',
      endereco: 'Rua Exemplo, 123 - Bairro, Santos, SP',
      email: 'joao@example.com',
      telefone: '(13) 99999-9999',
    },
  });
  
  const onSubmit = (values: z.infer<typeof formSchema>) => {
    // Em um app real, você enviaria esses dados para o backend
    console.log(values);
    
    toast({
      title: "Dados atualizados",
      description: "Suas informações pessoais foram atualizadas com sucesso!",
    });
    
    setEditOpen(false);
  };

  const onAlterarEmail = async (values: z.infer<typeof formSchema>) => {
    try {
      const response = await fetch('http://localhost:8080/alterarEmail', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: new URLSearchParams({
          'novo-email': values.email,
          'senha-atual': values.senhaAtual,
        }),
        credentials: 'include',
      });
      if (response.ok) {
        toast({
          title: "Email alterado com sucesso!",
          description: "Seu email foi atualizado.",
        });
        setEditOpen(false);
      } else {
        toast({
          title: "Erro ao alterar email",
          description: "Verifique os dados e tente novamente.",
          variant: "destructive",
        });
      }
    } catch (e) {
      toast({
        title: "Erro de conexão",
        description: "Não foi possível conectar ao servidor.",
        variant: "destructive",
      });
    }
  };

  const onAlterarTelefone = async (values: z.infer<typeof formSchema>) => {
    try {
      const response = await fetch('http://localhost:8080/alterarTelefone', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: new URLSearchParams({
          'novo-telefone': values.telefone,
          'senha-atual': values.senhaAtual,
        }),
        credentials: 'include',
      });
      if (response.ok) {
        toast({
          title: "Telefone alterado com sucesso!",
          description: "Seu telefone foi atualizado.",
        });
        setEditOpen(false);
      } else {
        toast({
          title: "Erro ao alterar telefone",
          description: "Verifique os dados e tente novamente.",
          variant: "destructive",
        });
      }
    } catch (e) {
      toast({
        title: "Erro de conexão",
        description: "Não foi possível conectar ao servidor.",
        variant: "destructive",
      });
    }
  };

  const onAlterarSenha = async (values: z.infer<typeof formSchema>) => {
    try {
      const response = await fetch('http://localhost:8080/alterarSenha', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: new URLSearchParams({
          'senha-atual': values.senhaAtual,
          'nova-senha': values.novaSenha,
          'repetir-nova-senha': values.repetirNovaSenha,
        }),
        credentials: 'include',
      });
      if (response.ok) {
        toast({
          title: "Senha alterada com sucesso!",
          description: "Sua senha foi atualizada.",
        });
        setEditOpen(false);
      } else {
        toast({
          title: "Erro ao alterar senha",
          description: "Verifique os dados e tente novamente.",
          variant: "destructive",
        });
      }
    } catch (e) {
      toast({
        title: "Erro de conexão",
        description: "Não foi possível conectar ao servidor.",
        variant: "destructive",
      });
    }
  };

  useEffect(() => {
    const fetchUserName = async () => {
      try {
        const response = await fetch('http://localhost:8080/getName', {
          method: 'GET',
          credentials: 'include',
        });
        if (response.ok) {
          const data = await response.json();
          setUserName(data.userName);
        } else {
          toast({
            title: "Erro ao carregar dados",
            description: "Não foi possível obter seus dados. Tente fazer login novamente.",
            variant: "destructive",
          });
        }
      } catch (e) {
        toast({
          title: "Erro de conexão",
          description: "Não foi possível conectar ao servidor.",
          variant: "destructive",
        });
      }
    };

    fetchUserName();
  }, [toast]);

  // Dados mockados para o exemplo
  const relatos: RelatoProps[] = [
    {
      id: '001',
      tipo: 'Vazamento',
      descricao: 'Vazamento de água na calçada próximo ao poste de luz',
      data: '10/05/2023',
      local: 'Av. Ana Costa, 123',
      status: 'resolvido'
    },
    {
      id: '002',
      tipo: 'Esgoto a céu aberto',
      descricao: 'Esgoto transbordando na rua e causando mau cheiro',
      data: '15/07/2023',
      local: 'Rua Conselheiro Nébias, 456',
      status: 'pendente'
    },
    {
      id: '003',
      tipo: 'Entupimento',
      descricao: 'Bueiro entupido causando alagamento em dias de chuva',
      data: '20/08/2023',
      local: 'Av. Presidente Wilson, 789',
      status: 'analise'
    }
  ];

  const statusLabels = {
    pendente: { label: 'Pendente', color: 'bg-amber-100 text-amber-800 border-amber-200' },
    analise: { label: 'Em Análise', color: 'bg-blue-100 text-blue-800 border-blue-200' },
    resolvido: { label: 'Resolvido', color: 'bg-green-100 text-green-800 border-green-200' },
    cancelado: { label: 'Cancelado', color: 'bg-red-100 text-red-800 border-red-200' },
  };
  
  return (
    <div className="container mx-auto py-10 px-4">
      <div className="flex flex-col lg:flex-row gap-8">
        {/* Sidebar perfil */}
        <div className="lg:w-1/3">
          <Card>
            <CardHeader className="flex flex-col items-center">
              <Avatar className="h-24 w-24 mb-4">
                <AvatarImage src="" />
                <AvatarFallback className="bg-[#18b190] text-white text-xl">JS</AvatarFallback>
              </Avatar>
              <CardTitle className="text-center">João Silva</CardTitle>
              <p className="text-sm text-muted-foreground mt-1">Membro desde Maio 2023</p>
              <div className="flex items-center gap-2 mt-2">
                <Badge className="bg-[#18b190]">3 Relatos</Badge>
                <Badge variant="outline">Cidadão Ativo</Badge>
              </div>
            </CardHeader>
            <CardContent>
              <div className="space-y-4">
                <div className="flex items-center gap-2">
                  <User className="h-4 w-4 text-muted-foreground" />
                  <span className="text-sm">João Silva</span>
                </div>
                <div className="flex items-center gap-2">
                  <MapPin className="h-4 w-4 text-muted-foreground" />
                  <span className="text-sm">Santos, SP</span>
                </div>
                <div className="flex items-center gap-2">
                  <FileText className="h-4 w-4 text-muted-foreground" />
                  <span className="text-sm">joao@example.com</span>
                </div>
                
                <Dialog open={editOpen} onOpenChange={setEditOpen}>
                  <DialogTrigger asChild>
                    <Button className="w-full mt-4 bg-[#18b190] hover:bg-[#149d79]">
                      Editar Perfil
                    </Button>
                  </DialogTrigger>
                  <DialogContent>
                    <DialogHeader>
                      <DialogTitle>Editar Perfil</DialogTitle>
                      <DialogDescription>
                        Atualize suas informações pessoais.
                      </DialogDescription>
                    </DialogHeader>
                    
                    <Form {...form}>
                      <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-4">
                        <FormField
                          control={form.control}
                          name="nome"
                          render={({ field }) => (
                            <FormItem>
                              <FormLabel>Nome Completo</FormLabel>
                              <FormControl>
                                <Input {...field} />
                              </FormControl>
                              <FormMessage />
                            </FormItem>
                          )}
                        />
                        
                        <FormField
                          control={form.control}
                          name="dataNascimento"
                          render={({ field }) => (
                            <FormItem>
                              <FormLabel>Data de Nascimento</FormLabel>
                              <FormControl>
                                <Input type="date" {...field} />
                              </FormControl>
                              <FormMessage />
                            </FormItem>
                          )}
                        />
                        
                        <FormField
                          control={form.control}
                          name="endereco"
                          render={({ field }) => (
                            <FormItem>
                              <FormLabel>Endereço</FormLabel>
                              <FormControl>
                                <Input {...field} />
                              </FormControl>
                              <FormMessage />
                            </FormItem>
                          )}
                        />
                        
                        <FormField
                          control={form.control}
                          name="email"
                          render={({ field }) => (
                            <FormItem>
                              <FormLabel>Email</FormLabel>
                              <FormControl>
                                <Input {...field} />
                              </FormControl>
                              <FormMessage />
                            </FormItem>
                          )}
                        />
                        
                        <FormField
                          control={form.control}
                          name="telefone"
                          render={({ field }) => (
                            <FormItem>
                              <FormLabel>Telefone</FormLabel>
                              <FormControl>
                                <Input {...field} />
                              </FormControl>
                              <FormMessage />
                            </FormItem>
                          )}
                        />
                        
                        <DialogFooter>
                          <Button variant="outline" onClick={() => setEditOpen(false)}>
                            Cancelar
                          </Button>
                          <Button type="submit" className="bg-[#18b190] hover:bg-[#149d79]">
                            Salvar
                          </Button>
                        </DialogFooter>
                      </form>
                    </Form>
                  </DialogContent>
                </Dialog>
              </div>
            </CardContent>
          </Card>
        </div>
        
        {/* Conteúdo principal */}
        <div className="lg:w-2/3">
          <Tabs defaultValue="relatos">
            <TabsList className="mb-6">
              <TabsTrigger value="relatos" className="data-[state=active]:bg-[#18b190] data-[state=active]:text-white">
                <FileText className="h-4 w-4 mr-2" />
                Meus Relatos
              </TabsTrigger>
              <TabsTrigger value="notificacoes" className="data-[state=active]:bg-[#18b190] data-[state=active]:text-white">
                <Bell className="h-4 w-4 mr-2" />
                Notificações
              </TabsTrigger>
              <TabsTrigger value="configuracoes" className="data-[state=active]:bg-[#18b190] data-[state=active]:text-white">
                <Settings className="h-4 w-4 mr-2" />
                Configurações
              </TabsTrigger>
            </TabsList>
            
            <TabsContent value="relatos">
              <div className="mb-4 flex justify-between items-center">
                <h2 className="text-2xl font-bold text-[#18b190]">Meus Relatos</h2>
                <Button asChild className="bg-[#18b190] hover:bg-[#149d79]">
                  <Link href="/relatar">
                    <PlusCircle className="h-4 w-4 mr-2" />
                    Novo Relato
                  </Link>
                </Button>
              </div>
              
              <div className="space-y-4">
                {relatos.map((relato) => (
                  <Card key={relato.id} className="overflow-hidden transition-all hover:shadow-md">
                    <CardContent className="p-0">
                      <div className="flex flex-col sm:flex-row">
                        <div className="p-4 sm:p-6 flex-grow">
                          <div className="flex items-center justify-between mb-2">
                            <h3 className="font-semibold">{relato.tipo}</h3>
                            <Badge className={statusLabels[relato.status].color}>
                              {statusLabels[relato.status].label}
                            </Badge>
                          </div>
                          <p className="text-sm text-gray-600 mb-2">{relato.descricao}</p>
                          <div className="flex flex-col sm:flex-row sm:items-center gap-2 sm:gap-4 text-xs text-gray-500">
                            <div className="flex items-center">
                              <MapPin className="h-3 w-3 mr-1" />
                              {relato.local}
                            </div>
                            <div>Relatado em: {relato.data}</div>
                          </div>
                          
                          <div className="mt-4 flex gap-2">
                            <Button variant="outline" size="sm">
                              Ver detalhes
                            </Button>
                            
                            {relato.status === 'pendente' && (
                              <Button variant="outline" size="sm" className="text-red-600 border-red-200 hover:bg-red-50">
                                Cancelar
                              </Button>
                            )}
                          </div>
                        </div>
                        
                        <div className={`w-2 sm:w-1 sm:h-auto flex-shrink-0 
                          ${relato.status === 'resolvido' ? 'bg-green-500' : 
                            relato.status === 'analise' ? 'bg-blue-500' : 
                            relato.status === 'pendente' ? 'bg-amber-500' : 'bg-red-500'}`}
                        />
                      </div>
                    </CardContent>
                  </Card>
                ))}
              </div>
            </TabsContent>
            
            <TabsContent value="notificacoes">
              <h2 className="text-2xl font-bold text-[#18b190] mb-4">Notificações</h2>
              
              <div className="space-y-4">
                <Card>
                  <CardContent className="p-4 flex items-start gap-4">
                    <div className="bg-green-100 p-2 rounded-full">
                      <Check className="h-5 w-5 text-green-600" />
                    </div>
                    <div className="flex-1">
                      <p className="font-medium">Relato resolvido</p>
                      <p className="text-sm text-gray-600">Seu relato sobre vazamento de água na Av. Ana Costa foi marcado como resolvido.</p>
                      <p className="text-xs text-gray-500 mt-1">3 dias atrás</p>
                    </div>
                  </CardContent>
                </Card>
                
                <Card>
                  <CardContent className="p-4 flex items-start gap-4">
                    <div className="bg-blue-100 p-2 rounded-full">
                      <AlertTriangle className="h-5 w-5 text-blue-600" />
                    </div>
                    <div className="flex-1">
                      <p className="font-medium">Relato em análise</p>
                      <p className="text-sm text-gray-600">Seu relato sobre bueiro entupido na Av. Presidente Wilson está em análise pela equipe técnica.</p>
                      <p className="text-xs text-gray-500 mt-1">1 semana atrás</p>
                    </div>
                  </CardContent>
                </Card>
              </div>
            </TabsContent>
            
            <TabsContent value="configuracoes">
              <h2 className="text-2xl font-bold text-[#18b190] mb-4">Configurações</h2>
              
              <Card>
                <CardContent className="p-6 space-y-4">
                  <div>
                    <h3 className="font-medium mb-2">Notificações</h3>
                    <div className="flex items-center justify-between">
                      <label htmlFor="email-notif" className="text-sm">Receber notificações por email</label>
                      <input type="checkbox" id="email-notif" className="h-4 w-4" defaultChecked />
                    </div>
                  </div>
                  
                  <div className="pt-4 border-t">
                    <h3 className="font-medium mb-2">Privacidade</h3>
                    <div className="flex items-center justify-between mb-2">
                      <label htmlFor="profile-public" className="text-sm">Perfil público</label>
                      <input type="checkbox" id="profile-public" className="h-4 w-4" />
                    </div>
                    <p className="text-xs text-gray-500">Quando ativado, seus relatados serão visíveis publicamente no mapa.</p>
                  </div>
                  
                  <div className="pt-4 border-t">
                    <h3 className="font-medium mb-2">Segurança</h3>
                    <Button variant="outline" className="w-full">Alterar Senha</Button>
                  </div>
                  
                  <div className="pt-4 border-t">
                    <h3 className="font-medium mb-2 text-red-600">Zona de perigo</h3>
                    <Button variant="outline" className="w-full text-red-600 border-red-200 hover:bg-red-50">
                      Excluir minha conta
                    </Button>
                  </div>
                </CardContent>
              </Card>
            </TabsContent>
          </Tabs>
        </div>
      </div>
    </div>
  );
}