import React from 'react';

interface LoadingAnimationProps {
  size?: number;
  text?: string;
}

export const LoadingAnimation: React.FC<LoadingAnimationProps> = ({ size = 24, text = "Loading..." }) => {
  return (
    <div className="flex flex-col items-center justify-center gap-2">
      <div
        className="animate-spin rounded-full border-4 border-t-transparent border-gray-500"
        style={{ width: size, height: size }}
      />
      {text && <span className="text-sm text-gray-600">{text}</span>}
    </div>
  );
};
