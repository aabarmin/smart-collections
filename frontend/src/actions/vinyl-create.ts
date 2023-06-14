import { create } from "zustand";

type State = {
  opened: boolean;
};

type Actions = {
  openDialog: () => void;
  closeDialog: () => void;
};

const useCreateVinyl = create<State & Actions>((set) => ({
  opened: false,
  openDialog: () => set((state) => ({ opened: true })),
  closeDialog: () => set((state) => ({ opened: false })),
}));

export default useCreateVinyl;
