'use client';

import { useState } from 'react';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import * as z from 'zod';
import { useToast } from '@/hooks/use-toast';
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
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from '@/components/ui/card';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import { Check, ChevronRight, Upload, MapPin, Calendar, Info } from 'lucide-react';
import { fetchAddressByCEP } from '@/lib/cep-service';

const formSchema = z.object({
  tipoProblema: z.string().min(1, { message: 'Selecione um tipo de problema' }),
  descricaoProblema: z.string().min(10, { message: 'A descrição deve ter pelo menos 10 caracteres' }),
  dataOcorrido: z.string().min(1, { message: 'Selecione uma data' }),
  cep: z.string().min(8, { message: 'CEP inválido' }).max(9),
  logradouro: z.string().min(1, { message: 'Informe o logradouro' }),
  numero: z.string().min(1, { message: 'Informe o número' }),
  bairro: z.string().min(1, { message: 'Informe o bairro' }),
  cidade: z.string().min(1, { message: 'Informe a cidade' }),
  estado: z.string().min(1, { message: 'Informe o estado' }),
});

export default function RelatarPage() {
  const [activeTab, setActiveTab] = useState('tipo');
  const [loading, setLoading] = useState(false);
  const { toast } = useToast();
  
  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      tipoProblema: '',
      descricaoProblema: '',
      dataOcorrido: '',
      cep: '',
      logradouro: '',
      numero: '',
      bairro: '',
      cidade: '',
      estado: '',
    },
  });

  const handleCEPSearch = async (cep: string) => {
    if (cep.length >= 8) {
      setLoading(true);
      try {
        const cleanCEP = cep.replace(/\D/g, '');
        const addressData = await fetchAddressByCEP(cleanCEP);
        
        if (addressData.erro) {
          toast({
            title: "CEP não encontrado",
            description: "Verifique o CEP informado e tente novamente.",
            variant: "destructive",
          });
        } else {
          form.setValue('logradouro', addressData.logradouro || '');
          form.setValue('bairro', addressData.bairro || '');
          form.setValue('cidade', addressData.localidade || '');
          form.setValue('estado', addressData.uf || '');
        }
      } catch (error) {
        toast({
          title: "Erro ao buscar CEP",
          description: "Ocorreu um erro ao buscar o endereço. Tente novamente.",
          variant: "destructive",
        });
      } finally {
        setLoading(false);
      }
    }
  };
  
  const onSubmit = (values: z.infer<typeof formSchema>) => {
    // Here you would send the data to your backend
    console.log(values);
    
    toast({
      title: "Problema relatado com sucesso!",
      description: "Obrigado por contribuir para melhorar o saneamento de Santos.",
    });
    
    // Reset form and go back to first tab
    form.reset();
    setActiveTab('tipo');
  };

  const goToNextTab = () => {
    if (activeTab === 'tipo') {
      setActiveTab('localidade');
    } else if (activeTab === 'localidade') {
      setActiveTab('resumo');
    }
  };

  const goToPreviousTab = () => {
    if (activeTab === 'localidade') {
      setActiveTab('tipo');
    } else if (activeTab === 'resumo') {
      setActiveTab('localidade');
    }
  };

  return (
    <div className="container mx-auto py-10 px-4">
      <Card className="max-w-3xl mx-auto">
        <CardHeader>
          <CardTitle className="text-[#18b190]">Relatar um Problema</CardTitle>
          <CardDescription>
            Ajude a melhorar o saneamento básico de Santos relatando problemas que você encontrar.
          </CardDescription>
        </CardHeader>
        <CardContent>
          <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-6">
              <Tabs value={activeTab} onValueChange={setActiveTab} className="w-full">
                <TabsList className="grid grid-cols-3 mb-8">
                  <TabsTrigger value="tipo" className="data-[state=active]:bg-[#18b190] data-[state=active]:text-white">
                    <Info className="h-4 w-4 mr-2" />
                    Problema
                  </TabsTrigger>
                  <TabsTrigger value="localidade" className="data-[state=active]:bg-[#18b190] data-[state=active]:text-white">
                    <MapPin className="h-4 w-4 mr-2" />
                    Localidade
                  </TabsTrigger>
                  <TabsTrigger value="resumo" className="data-[state=active]:bg-[#18b190] data-[state=active]:text-white">
                    <Check className="h-4 w-4 mr-2" />
                    Resumo
                  </TabsTrigger>
                </TabsList>
                
                <TabsContent value="tipo" className="space-y-4">
                  <FormField
                    control={form.control}
                    name="tipoProblema"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Tipo de Problema</FormLabel>
                        <Select 
                          onValueChange={field.onChange} 
                          defaultValue={field.value}
                        >
                          <FormControl>
                            <SelectTrigger>
                              <SelectValue placeholder="Selecione um tipo de problema" />
                            </SelectTrigger>
                          </FormControl>
                          <SelectContent>
                            <SelectItem value="esgoto_aberto">Esgoto a céu aberto: Vazamento ou acúmulo de esgoto em vias públicas</SelectItem>
                            <SelectItem value="agua_contaminada">Água contaminada: Água com odor, cor ou sabor estranho</SelectItem>
                            <SelectItem value="entupimento">Entupimento: Bueiros ou redes de esgoto entupidas</SelectItem>
                            <SelectItem value="alagamento">Alagamento: Pontos de alagamento recorrente</SelectItem>
                            <SelectItem value="vazamento">Vazamento: Vazamento de água em vias públicas</SelectItem>
                            <SelectItem value="outros">Outros</SelectItem>
                          </SelectContent>
                        </Select>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  
                  <FormField
                    control={form.control}
                    name="descricaoProblema"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Descrição do Problema</FormLabel>
                        <FormControl>
                          <Textarea 
                            placeholder="Descreva detalhadamente o problema encontrado..." 
                            className="min-h-[150px]" 
                            {...field} 
                          />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  
                  <FormField
                    control={form.control}
                    name="dataOcorrido"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Data do Ocorrido</FormLabel>
                        <FormControl>
                          <div className="flex items-center">
                            <Calendar className="mr-2 h-4 w-4 text-muted-foreground" />
                            <Input type="date" {...field} />
                          </div>
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  
                  <div className="flex justify-end mt-4">
                    <Button 
                      type="button" 
                      onClick={goToNextTab}
                      className="bg-[#18b190] hover:bg-[#149d79]"
                    >
                      Próximo
                      <ChevronRight className="ml-2 h-4 w-4" />
                    </Button>
                  </div>
                </TabsContent>
                
                <TabsContent value="localidade" className="space-y-4">
                  <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                    <FormField
                      control={form.control}
                      name="cep"
                      render={({ field }) => (
                        <FormItem className="md:col-span-2">
                          <FormLabel>CEP</FormLabel>
                          <FormControl>
                            <div className="flex space-x-2">
                              <Input 
                                {...field} 
                                placeholder="Digite o CEP" 
                                onChange={(e) => {
                                  field.onChange(e);
                                  const value = e.target.value.replace(/\D/g, '');
                                  if (value.length === 8) {
                                    handleCEPSearch(value);
                                  }
                                }}
                              />
                              <Button 
                                type="button" 
                                variant="outline" 
                                onClick={() => handleCEPSearch(field.value)}
                                disabled={loading || field.value.length < 8}
                              >
                                Buscar
                              </Button>
                            </div>
                          </FormControl>
                          <FormMessage />
                        </FormItem>
                      )}
                    />
                    
                    <FormField
                      control={form.control}
                      name="logradouro"
                      render={({ field }) => (
                        <FormItem>
                          <FormLabel>Logradouro</FormLabel>
                          <FormControl>
                            <Input {...field} placeholder="Rua, Avenida..." readOnly={loading} />
                          </FormControl>
                          <FormMessage />
                        </FormItem>
                      )}
                    />
                    
                    <FormField
                      control={form.control}
                      name="numero"
                      render={({ field }) => (
                        <FormItem>
                          <FormLabel>Número</FormLabel>
                          <FormControl>
                            <Input {...field} placeholder="Número" />
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
                            <Input {...field} placeholder="Bairro" readOnly={loading} />
                          </FormControl>
                          <FormMessage />
                        </FormItem>
                      )}
                    />
                    
                    <FormField
                      control={form.control}
                      name="cidade"
                      render={({ field }) => (
                        <FormItem>
                          <FormLabel>Cidade</FormLabel>
                          <FormControl>
                            <Input {...field} placeholder="Cidade" readOnly={loading} />
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
                            <Input {...field} placeholder="Estado" readOnly={loading} />
                          </FormControl>
                          <FormMessage />
                        </FormItem>
                      )}
                    />
                  </div>
                  
                  <div className="flex justify-between mt-4">
                    <Button 
                      type="button" 
                      variant="outline" 
                      onClick={goToPreviousTab}
                    >
                      Voltar
                    </Button>
                    <Button 
                      type="button" 
                      onClick={goToNextTab}
                      className="bg-[#18b190] hover:bg-[#149d79]"
                    >
                      Próximo
                      <ChevronRight className="ml-2 h-4 w-4" />
                    </Button>
                  </div>
                </TabsContent>
                
                <TabsContent value="resumo" className="space-y-4">
                  <div className="bg-gray-50 p-4 rounded-lg space-y-4">
                    <div>
                      <h3 className="font-medium text-sm text-gray-500">Tipo de Problema</h3>
                      <p className="font-medium">{form.watch('tipoProblema') ? 
                        form.watch('tipoProblema').replace('_', ' ').charAt(0).toUpperCase() + form.watch('tipoProblema').replace('_', ' ').slice(1) : 
                        'Não informado'}</p>
                    </div>
                    
                    <div>
                      <h3 className="font-medium text-sm text-gray-500">Descrição</h3>
                      <p>{form.watch('descricaoProblema') || 'Não informado'}</p>
                    </div>
                    
                    <div>
                      <h3 className="font-medium text-sm text-gray-500">Data do Ocorrido</h3>
                      <p>{form.watch('dataOcorrido') || 'Não informado'}</p>
                    </div>
                    
                    <div>
                      <h3 className="font-medium text-sm text-gray-500">Endereço</h3>
                      <p>
                        {form.watch('logradouro')}, {form.watch('numero')}, {form.watch('bairro')}, {form.watch('cidade')}-{form.watch('estado')}, {form.watch('cep')}
                      </p>
                    </div>
                  </div>
                  
                  <div className="bg-blue-50 border-l-4 border-blue-500 p-4 rounded-r-lg">
                    <p className="text-blue-700 text-sm">
                      Você também pode anexar uma foto do problema. Clique no botão abaixo para fazer upload.
                    </p>
                    <Button type="button" variant="outline" className="mt-2 text-blue-700">
                      <Upload className="mr-2 h-4 w-4" />
                      Anexar foto (opcional)
                    </Button>
                  </div>
                  
                  <div className="flex justify-between mt-4">
                    <Button 
                      type="button" 
                      variant="outline" 
                      onClick={goToPreviousTab}
                    >
                      Voltar
                    </Button>
                    <Button 
                      type="submit"
                      className="bg-[#18b190] hover:bg-[#149d79]"
                    >
                      Enviar Relato
                    </Button>
                  </div>
                </TabsContent>
              </Tabs>
            </form>
          </Form>
        </CardContent>
        <CardFooter className="flex justify-center border-t pt-6">
          <p className="text-sm text-gray-500 text-center">
            Todos os relatos são verificados por nossa equipe antes de serem processados.
            Agradecemos sua contribuição para melhorar o saneamento de Santos.
          </p>
        </CardFooter>
      </Card>
    </div>
  );
}