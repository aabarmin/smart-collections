import { create } from "zustand";

type State = {
  toasts: string[];
};

type Action = {
  showToast: (toast: string) => void;
  removeFirst: () => void;
};

const useToasts = create<State & Action>((set) => ({
  toasts: [],
  showToast: (toast: string) =>
    set((state: State) => {
      const toasts = state.toasts;
      toasts.push(toast);
      return { toasts };
    }),
  removeFirst: () =>
    set((state: State) => {
      return { toasts: [] };
    }),
}));

export default useToasts;
