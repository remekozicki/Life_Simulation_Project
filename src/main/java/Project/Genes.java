package Project;

import java.util.Arrays;

public class Genes {

    private int[] genes;
    private int size;

//    constructor for starting animals' genes
    public Genes(int size) {
        this.size = size;
        this.genes = new int[size];

        for (int i = 0; i < size; i++) {
            this.genes[i] = (int) (Math.random() * 8);
        }
    }

    public Genes(Genes g) {
        this.genes = Arrays.copyOf(g.getGenes(), g.getSize());
        this.size = g.getSize();
    }

//    constructor for genes for the children
    public Genes(Genes g1, Genes g2, int placeToDiv, boolean isRandom) {

        this(g1.getSize());

//        choosing the side for stronger genes, will have to do it in animal class
//        int rand = (int) (Math.random() * 2);

        for (int i = 0; i <= placeToDiv; i++) {
            this.genes[i] = g1.getGenes()[i];
        }

        for (int i = placeToDiv + 1; i < this.size; i++) {
            this.genes[i] = g2.getGenes()[i];
        }

        this.mutate(isRandom);
    }

    public void mutate(boolean isRandom) {

        int noOfMutations = (int) (Math.random() * (this.getSize() + 1));

        boolean[] alreadyMutated = new boolean[this.getSize()];

        for (int i = 0; i < noOfMutations; i++) {

            int geneToMutate = (int) (Math.random() * this.getSize());
            int tries = 0;

            while (alreadyMutated[geneToMutate]) {
                if (tries == 20) {
//                    it means that we tried to find unmutated gene too many times
                    for (int j = 0; j < this.getSize(); j++) {
                        if (!alreadyMutated[j]) {
                            geneToMutate = j;
                            break;
                        }
                    }

                    break;
                }
                geneToMutate = (int) (Math.random() * this.getSize());
                tries++;
            }

            alreadyMutated[geneToMutate] = true;

            if (isRandom) {
                this.genes[geneToMutate] = (int) (Math.random() * 8);
            } else {
                int val;
                if ((int) (Math.random() * 2) == 1) {
                    val = -1;
                } else {
                    val = 1;
                }

                this.genes[geneToMutate] += val;

                if (this.genes[geneToMutate] == 8) {
                    this.genes[geneToMutate] = 0;
                }
                if (this.genes[geneToMutate] == -1) {
                    this.genes[geneToMutate] = 7;
                }
            }
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(this.genes);
    }

    public int[] getGenes() {
        return this.genes;
    }

    public int getSize() {
        return this.size;
    }

}
