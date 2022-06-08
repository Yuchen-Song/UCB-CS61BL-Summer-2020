public class DNASequenceSet {
    private DNANode sentinel;

    public DNASequenceSet() {
        sentinel = new DNANode(-1, false);
    }

    public void add(int[] sequence) {
        DNANode pointer = sentinel;
        for (int i = 0; i < sequence.length; i++) {
            int base = sequence[i];
            if (pointer == null) {
                pointer = new DNANode(base, false);
            }
            pointer = pointer.nexts[i];
        }
        pointer.endOfSequence = true;
    }

    public boolean contains(int[] sequence) {
        DNANode pointer = sentinel;
        for (int i = 0; i < sequence.length; i++) {
            int base = sequence[i];
            if (pointer == null) {
                return false;
            }
            pointer = pointer.nexts[i];
        }
        return true;
    }


    public static class DNANode {
        private int base;
        private boolean endOfSequence;
        private DNANode[] nexts;

        public DNANode(int b, boolean end) {
            this.base = b;
            this.endOfSequence = end;
            this.nexts = new DNANode[4];
        }
    }
}