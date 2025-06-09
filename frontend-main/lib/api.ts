import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080',
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Interceptor para adicionar o token em todas as requisições
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// Interceptor para tratar erros
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem('token');
      localStorage.removeItem('userId');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export const login = async (email: string, senha: string) => {
  try {
    const response = await api.post('/loginUsuario', { email, senha });
    console.log('Login API response:', response.data); // Debug
    
    if (response.data.token) {
      localStorage.setItem('token', response.data.token);
      localStorage.setItem('userId', response.data.userId);
      console.log('Token and userId saved:', { 
        token: response.data.token, 
        userId: response.data.userId 
      }); // Debug
    } else {
      console.error('No token in response:', response.data); // Debug
      throw new Error('Token não recebido do servidor');
    }
    
    return response.data;
  } catch (error) {
    console.error('Login API error:', error); // Debug
    throw error;
  }
};

export const logout = () => {
  localStorage.removeItem('token');
  localStorage.removeItem('userId');
};

export const cadastrarUsuario = async (dados: {
  nome: string;
  email: string;
  telefone: string;
  cpf: string;
  senha: string;
}) => {
  const response = await api.post('/cadastroUsuario', dados);
  return response.data;
};

export const getUserName = async () => {
  try {
    const response = await api.get('/perfil');
    console.log('getUserName response:', response.data); // Debug
    return response.data;
  } catch (error) {
    console.error('getUserName error:', error); // Debug
    throw error;
  }
};

export const alterarEmail = async (novoEmail: string, senhaAtual: string) => {
  const response = await api.post('/alterarEmail', {
    'novo-email': novoEmail,
    'senha-atual': senhaAtual,
  });
  return response.data;
};

export const alterarTelefone = async (novoTelefone: string, senhaAtual: string) => {
  const response = await api.post('/alterarTelefone', {
    'novo-telefone': novoTelefone,
    'senha-atual': senhaAtual,
  });
  return response.data;
};

export const alterarSenha = async (senhaAtual: string, novaSenha: string, repetirNovaSenha: string) => {
  const response = await api.post('/alterarSenha', {
    'senha-atual': senhaAtual,
    'nova-senha': novaSenha,
    'repetir-nova-senha': repetirNovaSenha,
  });
  return response.data;
};

export const cadastrarRelato = async (dados: {
  tipo_problema: string;
  descricao: string;
  data_ocorrido: string;
  cep: string;
  rua: string;
  numero: string;
  bairro: string;
  cidade: string;
  estado: string;
}) => {
  const response = await api.post('/relatar', dados);
  return response.data;
};

export const atualizarPerfil = async (dados: {
  nome: string;
  email: string;
  telefone: string;
  dataNascimento: string;
  endereco: string;
}) => {
  const response = await api.put('/perfil', dados);
  return response.data;
};

export default api; 