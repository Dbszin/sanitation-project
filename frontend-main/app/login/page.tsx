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
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from '@/components/ui/card';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import { useToast } from '@/hooks/use-toast';
import Link from 'next/link';
import { LogIn, UserPlus, Mail, KeyRound, User, Calendar, Phone } from 'lucide-react';

const loginSchema = z.object({
  email: z.string().email({ message: 'Email inválido' }),
  senha: z.string().min(6, { message: 'A senha deve ter pelo menos 6 caracteres' }),
});

const cadastroSchema = z.object({
  nome: z.string().min(3, { message: 'O nome deve ter pelo menos 3 caracteres' }),
  dataNascimento: z.string().min(1, { message: 'Data de nascimento é obrigatória' }),
  cpf: z.string().min(11, { message: 'CPF inválido' }).max(14),
  email: z.string().email({ message: 'Email inválido' }),
  telefone: z.string().min(10, { message: 'Telefone inválido' }),
  senha: z.string().min(6, { message: 'A senha deve ter pelo menos 6 caracteres' }),
});

export default function LoginPage() {
  const [activeTab, setActiveTab] = useState('login');
  const router = useRouter();
  const { toast } = useToast();
  
  const loginForm = useForm<z.infer<typeof loginSchema>>({
    resolver: zodResolver(loginSchema),
    defaultValues: {
      email: '',
      senha: '',
    },
  });
  
  const cadastroForm = useForm<z.infer<typeof cadastroSchema>>({
    resolver: zodResolver(cadastroSchema),
    defaultValues: {
      nome: '',
      dataNascimento: '',
      cpf: '',
      email: '',
      telefone: '',
      senha: '',
    },
  });
  
  const onLoginSubmit = async (values: z.infer<typeof loginSchema>) => {
    try {
      const response = await fetch('http://localhost:8080/Logar', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: new URLSearchParams(values as any),
        credentials: 'include',
      });
      if (response.ok) {
        toast({
          title: "Login realizado com sucesso!",
          description: "Redirecionando para sua área de usuário...",
        });
        setTimeout(() => {
          router.push('/perfil');
        }, 1000);
      } else {
        toast({
          title: "Erro ao logar",
          description: "Verifique suas credenciais.",
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
  
  const onCadastroSubmit = async (values: z.infer<typeof cadastroSchema>) => {
    try {
      const response = await fetch('http://localhost:8080/Cadastrar', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: new URLSearchParams({
          nome: values.nome,
          email: values.email,
          telefone: values.telefone,
          cpf: values.cpf,
          senha: values.senha,
        }),
      });
      if (response.ok) {
        toast({
          title: "Cadastro realizado com sucesso!",
          description: "Você já pode fazer login com suas credenciais.",
        });
        setActiveTab('login');
        cadastroForm.reset();
      } else {
        toast({
          title: "Erro ao cadastrar",
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

  return (
    <div className="container mx-auto py-10 px-4 flex items-center justify-center min-h-[calc(100vh-200px)]">
      <Card className="w-full max-w-md">
        <CardHeader className="space-y-1">
          <CardTitle className="text-2xl font-bold text-center text-[#18b190]">
            {activeTab === 'login' ? 'Acesse sua conta' : 'Crie sua conta'}
          </CardTitle>
          <CardDescription className="text-center">
            {activeTab === 'login' 
              ? 'Entre com seu email e senha para continuar' 
              : 'Preencha seus dados para criar uma nova conta'}
          </CardDescription>
        </CardHeader>
        <CardContent>
          <Tabs value={activeTab} onValueChange={setActiveTab} className="w-full">
            <TabsList className="grid grid-cols-2 mb-6">
              <TabsTrigger value="login" className="data-[state=active]:bg-[#18b190] data-[state=active]:text-white">
                <LogIn className="h-4 w-4 mr-2" />
                Login
              </TabsTrigger>
              <TabsTrigger value="cadastro" className="data-[state=active]:bg-[#18b190] data-[state=active]:text-white">
                <UserPlus className="h-4 w-4 mr-2" />
                Cadastro
              </TabsTrigger>
            </TabsList>
            
            <TabsContent value="login">
              <Form {...loginForm}>
                <form onSubmit={loginForm.handleSubmit(onLoginSubmit)} className="space-y-4">
                  <FormField
                    control={loginForm.control}
                    name="email"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Email</FormLabel>
                        <FormControl>
                          <div className="flex items-center border rounded-md px-3 focus-within:ring-1 focus-within:ring-[#18b190] focus-within:border-[#18b190]">
                            <Mail className="h-4 w-4 text-gray-400 mr-2" />
                            <Input 
                              {...field} 
                              placeholder="seu@email.com" 
                              className="border-0 focus-visible:ring-0 focus-visible:ring-offset-0" 
                            />
                          </div>
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  
                  <FormField
                    control={loginForm.control}
                    name="senha"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Senha</FormLabel>
                        <FormControl>
                          <div className="flex items-center border rounded-md px-3 focus-within:ring-1 focus-within:ring-[#18b190] focus-within:border-[#18b190]">
                            <KeyRound className="h-4 w-4 text-gray-400 mr-2" />
                            <Input 
                              {...field} 
                              type="password"
                              placeholder="******" 
                              className="border-0 focus-visible:ring-0 focus-visible:ring-offset-0" 
                            />
                          </div>
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  
                  <div className="text-right">
                    <Link href="#" className="text-sm text-[#18b190] hover:underline">
                      Esqueceu sua senha?
                    </Link>
                  </div>
                  
                  <Button 
                    type="submit" 
                    className="w-full bg-[#18b190] hover:bg-[#149d79]"
                  >
                    Entrar
                  </Button>
                </form>
              </Form>
            </TabsContent>
            
            <TabsContent value="cadastro">
              <Form {...cadastroForm}>
                <form onSubmit={cadastroForm.handleSubmit(onCadastroSubmit)} className="space-y-4">
                  <FormField
                    control={cadastroForm.control}
                    name="nome"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Nome Completo</FormLabel>
                        <FormControl>
                          <div className="flex items-center border rounded-md px-3 focus-within:ring-1 focus-within:ring-[#18b190] focus-within:border-[#18b190]">
                            <User className="h-4 w-4 text-gray-400 mr-2" />
                            <Input 
                              {...field} 
                              placeholder="Seu nome completo" 
                              className="border-0 focus-visible:ring-0 focus-visible:ring-offset-0" 
                            />
                          </div>
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  
                  <div className="grid grid-cols-2 gap-4">
                    <FormField
                      control={cadastroForm.control}
                      name="dataNascimento"
                      render={({ field }) => (
                        <FormItem>
                          <FormLabel>Data de Nascimento</FormLabel>
                          <FormControl>
                            <div className="flex items-center border rounded-md px-3 focus-within:ring-1 focus-within:ring-[#18b190] focus-within:border-[#18b190]">
                              <Calendar className="h-4 w-4 text-gray-400 mr-2" />
                              <Input 
                                {...field} 
                                type="date"
                                className="border-0 focus-visible:ring-0 focus-visible:ring-offset-0" 
                              />
                            </div>
                          </FormControl>
                          <FormMessage />
                        </FormItem>
                      )}
                    />
                    
                    <FormField
                      control={cadastroForm.control}
                      name="cpf"
                      render={({ field }) => (
                        <FormItem>
                          <FormLabel>CPF</FormLabel>
                          <FormControl>
                            <Input 
                              {...field} 
                              placeholder="000.000.000-00" 
                              onChange={(e) => {
                                const value = e.target.value.replace(/\D/g, '');
                                const formattedValue = value
                                  .replace(/(\d{3})(\d)/, '$1.$2')
                                  .replace(/(\d{3})(\d)/, '$1.$2')
                                  .replace(/(\d{3})(\d{1,2})$/, '$1-$2')
                                  .substring(0, 14);
                                field.onChange(formattedValue);
                              }}
                            />
                          </FormControl>
                          <FormMessage />
                        </FormItem>
                      )}
                    />
                  </div>
                  
                  <FormField
                    control={cadastroForm.control}
                    name="email"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Email</FormLabel>
                        <FormControl>
                          <div className="flex items-center border rounded-md px-3 focus-within:ring-1 focus-within:ring-[#18b190] focus-within:border-[#18b190]">
                            <Mail className="h-4 w-4 text-gray-400 mr-2" />
                            <Input 
                              {...field} 
                              placeholder="seu@email.com" 
                              className="border-0 focus-visible:ring-0 focus-visible:ring-offset-0" 
                            />
                          </div>
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  
                  <FormField
                    control={cadastroForm.control}
                    name="telefone"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Telefone</FormLabel>
                        <FormControl>
                          <div className="flex items-center border rounded-md px-3 focus-within:ring-1 focus-within:ring-[#18b190] focus-within:border-[#18b190]">
                            <Phone className="h-4 w-4 text-gray-400 mr-2" />
                            <Input 
                              {...field} 
                              placeholder="(00) 00000-0000"
                              onChange={(e) => {
                                const value = e.target.value.replace(/\D/g, '');
                                const formattedValue = value
                                  .replace(/^(\d{2})(\d)/g, '($1) $2')
                                  .replace(/(\d)(\d{4})$/, '$1-$2')
                                  .substring(0, 15);
                                field.onChange(formattedValue);
                              }}
                              className="border-0 focus-visible:ring-0 focus-visible:ring-offset-0" 
                            />
                          </div>
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  
                  <FormField
                    control={cadastroForm.control}
                    name="senha"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Senha</FormLabel>
                        <FormControl>
                          <div className="flex items-center border rounded-md px-3 focus-within:ring-1 focus-within:ring-[#18b190] focus-within:border-[#18b190]">
                            <KeyRound className="h-4 w-4 text-gray-400 mr-2" />
                            <Input 
                              {...field} 
                              type="password"
                              placeholder="******" 
                              className="border-0 focus-visible:ring-0 focus-visible:ring-offset-0" 
                            />
                          </div>
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  
                  <Button 
                    type="submit" 
                    className="w-full bg-[#18b190] hover:bg-[#149d79]"
                  >
                    Cadastrar
                  </Button>
                </form>
              </Form>
            </TabsContent>
          </Tabs>
        </CardContent>
        <CardFooter className="flex justify-center border-t pt-6">
          <p className="text-sm text-center text-gray-500">
            Ao continuar, você concorda com os{' '}
            <Link href="#" className="text-[#18b190] hover:underline">
              Termos de Serviço
            </Link>{' '}
            e{' '}
            <Link href="#" className="text-[#18b190] hover:underline">
              Política de Privacidade
            </Link>.
          </p>
        </CardFooter>
      </Card>
    </div>
  );
}