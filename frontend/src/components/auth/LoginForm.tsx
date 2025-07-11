import React, { useState } from 'react';
import { Eye, EyeOff, Package } from 'lucide-react';
import { Button } from '../common/Button';
import type { User } from '../../types';

interface LoginFormProps {
  onLogin: (user: User, token: string) => void;
  onRegister?: () => void;
}

export const LoginForm: React.FC<LoginFormProps> = ({ onLogin, onRegister }) => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);

    // Mock authentication - replace with actual API call
    setTimeout(() => {
      const mockUser: User = {
        id: '1',
        email: email,
        firstName: 'John',
        lastName: 'Doe',
        role: 'STORE_MANAGER' // Change this to 'ADMIN', 'STORE_MANAGER', 'PRODUCTION_OFFICER', or 'SALES_OFFICER'
      };
      
      onLogin(mockUser, 'mock-jwt-token');
      setLoading(false);
    }, 1000);
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-slate-900 via-slate-800 to-emerald-900">
      <div className="max-w-md w-full space-y-8 p-8">
        <div className="text-center">
          <div className="flex justify-center mb-4">
            <div className="w-16 h-16 bg-gradient-to-br from-emerald-400 to-cyan-500 rounded-2xl flex items-center justify-center">
              <Package className="w-8 h-8 text-white" />
            </div>
          </div>
          <h2 className="text-3xl font-bold text-white">Welcome back</h2>
          <p className="mt-2 text-sm text-gray-300">
            Sign in to your FabricFlow account
          </p>
        </div>
        
        <form onSubmit={handleSubmit} className="mt-8 space-y-6">
          <div className="space-y-4">
            <div>
              <label htmlFor="email" className="sr-only">
                Email address
              </label>
              <input
                id="email"
                name="email"
                type="email"
                required
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                className="relative block w-full px-3 py-3 border border-gray-600 bg-gray-700 text-white placeholder-gray-400 rounded-lg focus:outline-none focus:ring-2 focus:ring-emerald-500 focus:border-emerald-500 focus:z-10 sm:text-sm"
                placeholder="Email address"
              />
            </div>
            
            <div className="relative">
              <label htmlFor="password" className="sr-only">
                Password
              </label>
              <input
                id="password"
                name="password"
                type={showPassword ? 'text' : 'password'}
                required
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                className="relative block w-full px-3 py-3 pr-10 border border-gray-600 bg-gray-700 text-white placeholder-gray-400 rounded-lg focus:outline-none focus:ring-2 focus:ring-emerald-500 focus:border-emerald-500 focus:z-10 sm:text-sm"
                placeholder="Password"
              />
              <button
                type="button"
                onClick={() => setShowPassword(!showPassword)}
                className="absolute inset-y-0 right-0 pr-3 flex items-center text-gray-400 hover:text-gray-300"
              >
                {showPassword ? (
                  <EyeOff className="w-5 h-5" />
                ) : (
                  <Eye className="w-5 h-5" />
                )}
              </button>
            </div>
          </div>

          <div>
            <Button
              type="submit"
              loading={loading}
              className="group relative w-full flex justify-center py-3 px-4 text-sm font-medium"
            >
              Sign in
            </Button>
          </div>
        </form>
        
        <div className="text-center space-y-3">
          <p className="text-xs text-gray-400">
            Demo credentials: admin@example.com / password
          </p>
          
          <div className="flex items-center justify-center">
            <div className="border-t border-gray-600 flex-grow"></div>
            <span className="px-3 text-xs text-gray-400">or</span>
            <div className="border-t border-gray-600 flex-grow"></div>
          </div>
          
          <div>
            <button
              type="button"
              onClick={onRegister}
              className="w-full flex justify-center py-3 px-4 border border-emerald-500 text-emerald-400 bg-transparent hover:bg-emerald-500 hover:text-white rounded-lg text-sm font-medium transition-colors duration-200 focus:outline-none focus:ring-2 focus:ring-emerald-500 focus:ring-offset-2 focus:ring-offset-gray-800"
            >
              Create New Account
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};