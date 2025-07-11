import React from 'react';
import { AlertCircle, Home, RefreshCw, ArrowLeft } from 'lucide-react';
import { Button } from './Button';

interface ErrorPageProps {
  errorCode?: string;
  errorMessage?: string;
  onRetry?: () => void;
  onGoHome?: () => void;
  onGoBack?: () => void;
  showRetry?: boolean;
  showGoHome?: boolean;
  showGoBack?: boolean;
}

export const ErrorPage: React.FC<ErrorPageProps> = ({
  errorCode = '404',
  errorMessage = 'Page not found',
  onRetry,
  onGoHome,
  onGoBack,
  showRetry = true,
  showGoHome = true,
  showGoBack = true
}) => {
  const getErrorTitle = () => {
    switch (errorCode) {
      case '404':
        return 'Page Not Found';
      case '500':
        return 'Internal Server Error';
      case '403':
        return 'Access Denied';
      case '401':
        return 'Unauthorized';
      default:
        return 'Something Went Wrong';
    }
  };

  const getErrorDescription = () => {
    switch (errorCode) {
      case '404':
        return 'The page you are looking for might have been removed, had its name changed, or is temporarily unavailable.';
      case '500':
        return 'We are experiencing some technical difficulties. Please try again later.';
      case '403':
        return 'You do not have permission to access this resource.';
      case '401':
        return 'You need to be authenticated to access this resource.';
      default:
        return errorMessage || 'An unexpected error occurred. Please try again.';
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-slate-900 via-slate-800 to-emerald-900">
      <div className="max-w-md w-full space-y-8 p-8">
        <div className="text-center">
          <div className="flex justify-center mb-4">
            <div className="w-16 h-16 bg-gradient-to-br from-red-400 to-orange-500 rounded-2xl flex items-center justify-center">
              <AlertCircle className="w-8 h-8 text-white" />
            </div>
          </div>
          
          <div className="mb-4">
            <h1 className="text-6xl font-bold text-white mb-2">
              {errorCode}
            </h1>
            <h2 className="text-2xl font-bold text-white">
              {getErrorTitle()}
            </h2>
          </div>
          
          <p className="mt-4 text-sm text-gray-300 leading-relaxed">
            {getErrorDescription()}
          </p>
        </div>
        
        <div className="space-y-4">
          {showRetry && onRetry && (
            <Button
              onClick={onRetry}
              className="group relative w-full flex justify-center py-3 px-4 text-sm font-medium"
            >
              <RefreshCw className="w-4 h-4 mr-2" />
              Try Again
            </Button>
          )}
          
          {showGoHome && onGoHome && (
            <button
              onClick={onGoHome}
              className="w-full flex justify-center items-center py-3 px-4 border border-emerald-500 text-emerald-400 bg-transparent hover:bg-emerald-500 hover:text-white rounded-lg text-sm font-medium transition-colors duration-200 focus:outline-none focus:ring-2 focus:ring-emerald-500 focus:ring-offset-2 focus:ring-offset-gray-800"
            >
              <Home className="w-4 h-4 mr-2" />
              Go to Dashboard
            </button>
          )}
          
          {showGoBack && onGoBack && (
            <button
              onClick={onGoBack}
              className="w-full flex justify-center items-center py-3 px-4 text-gray-400 hover:text-white bg-transparent hover:bg-gray-700 rounded-lg text-sm font-medium transition-colors duration-200 focus:outline-none focus:ring-2 focus:ring-gray-500 focus:ring-offset-2 focus:ring-offset-gray-800"
            >
              <ArrowLeft className="w-4 h-4 mr-2" />
              Go Back
            </button>
          )}
        </div>
        
        <div className="text-center">
          <p className="text-xs text-gray-400">
            If the problem persists, please contact support
          </p>
        </div>
      </div>
    </div>
  );
};