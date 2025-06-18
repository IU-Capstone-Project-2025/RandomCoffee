import { ReactNode, useEffect, useState } from 'react';
import { Modal } from '@telegram-apps/telegram-ui';
import { ModalProps } from '@telegram-apps/telegram-ui/dist/components/Overlays/Modal/Modal';

interface FancyModalProps extends Omit<ModalProps, 'overlayComponent'> {
  children: ReactNode;
  header?: ReactNode;
}

export const FancyModal = ({ 
  children, 
  open, 
  onOpenChange, 
  header, 
  ...props 
}: FancyModalProps) => {
  const [showOverlay, setShowOverlay] = useState(false);
  
  useEffect(() => {
    if (open) {
      setShowOverlay(true);
    } else {
      setTimeout(() => {
        setShowOverlay(false);
      }, 300); // Match the duration of the fade-out transition
    }
  }, [open]);

  return (
    <>
      {/* Custom overlay */}
      <div 
        className="fixed w-screen h-screen bg-black/75 top-0 left-0 transition-opacity duration-300 ease-in-out z-10"
        style={{
          opacity: open ? 1 : 0,
          pointerEvents: showOverlay ? 'auto' : 'none',
        }}
      />
      
      <Modal
        open={open}
        onOpenChange={onOpenChange}
        header={header}
        className="z-20"
        overlayComponent={<div></div>}
        {...props}
      >
        {children}
      </Modal>
    </>
  );
};

export default FancyModal; 