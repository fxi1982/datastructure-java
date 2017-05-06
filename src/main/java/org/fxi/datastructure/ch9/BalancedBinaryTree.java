package org.fxi.datastructure.ch9;

public class BalancedBinaryTree {

    static class Node {
        public final int key;
        Node(int key) {
            this.key = key;
        }
    }

    static class InsertResult {
        public final boolean inserted;
        public final boolean taller;

        InsertResult(boolean inserted, boolean taller) {
            this.inserted = inserted;
            this.taller = taller;
        }
    }

    static class DeleteResult {
        public final boolean deleted;
        public final boolean shorter;
        public final int key;

        DeleteResult(boolean deleted, boolean shorter, int key) {
            this.deleted = deleted;
            this.shorter = shorter;
            this.key =  key;
        }

        DeleteResult(boolean deleted, boolean shorter) {
            this(deleted, shorter, 0);
        }
    }

    private Node root;
    private int bf = 0;
    private BalancedBinaryTree leftChild;
    private BalancedBinaryTree rightChild;

    public BalancedBinaryTree() {
    }

    public InsertResult insert(int key) {
        boolean taller = false;
        boolean inserted = false;

        if (root == null) {
            root = new Node(key);
            leftChild = null;
            rightChild = null;
            taller = true;
            inserted = true;
        } else {
            if (key > root.key) {
                if (rightChild == null) {
                    rightChild = new BalancedBinaryTree();
                }
                InsertResult result = rightChild.insert(key);
                if (result.inserted && result.taller) {
                    inserted = true;
                    switch (bf) {
                        case 1:
                            bf = 0;
                            taller = false;
                            break;
                        case 0:
                            bf = -1;
                            taller = true;
                            break;
                        case -1:
                            rightBalance();
                            taller = false;
                            break;
                        default:
                            // do nothing
                    }
                }
            } else if (key < root.key){
                if (leftChild == null) {
                    leftChild = new BalancedBinaryTree();
                }
                InsertResult result = leftChild.insert(key);
                if (result.inserted && result.taller) {
                    inserted = true;
                    switch (bf) {
                        case 1:
                            leftBalance();
                            taller=false;
                            break;
                        case 0:
                            bf = 1;
                            taller = true;
                            break;
                        case -1:
                            bf = 0;
                            taller = false;
                            break;
                        default:
                            // do nothing.
                    }
                }
            } else {
                // do nothing.
            }
        }

        return new InsertResult(inserted, taller);
    }

    public boolean leftBalance() {
        BalancedBinaryTree lc = leftChild;
        boolean shorter = true;
        switch (lc.bf) {
            case 1:
                bf = lc.bf = 0;
                rightRotate();
                break;
            case 0:
                BalancedBinaryTree rc = new BalancedBinaryTree();
                rc.insert(root.key);
                rc.bf = bf;
                rc.rightChild = rightChild;
                rc.leftChild = lc.rightChild;
                root = new Node(lc.root.key);
                bf = -1;
                rightChild = rc;
                leftChild = lc.leftChild;
                shorter = false;
                break;
            case -1:
                BalancedBinaryTree rd = lc.rightChild;
                switch (rd.bf) {
                    case 1:
                        bf = -1;
                        lc.bf = 0;
                        break;
                    case 0:
                        bf = lc.bf = 0;
                        break;
                    case -1:
                        bf = 0;
                        lc.bf = 1;
                        break;
                    default:
                        // do nothing
                }
                rd.bf = 0;
                leftChild.leftRotate();
                rightRotate();
                break;
            default:
                // do nothing
        }

        return shorter;
    }

    public boolean rightBalance() {
        BalancedBinaryTree rc = rightChild;
        boolean shorter = true;
        switch (rc.bf) {
            case -1:
                bf = rc.bf = 0;
                leftRotate();
                break;
            case 0:
                BalancedBinaryTree lc = new BalancedBinaryTree();
                lc.insert(root.key);
                lc.bf = bf;
                lc.leftChild = leftChild;
                lc.rightChild = rc.leftChild;
                root = new Node(rc.root.key);
                bf = 1;
                leftChild = lc;
                rightChild = rc.rightChild;
                shorter = false;
                break;
            case 1:
                BalancedBinaryTree rd = rc.leftChild;
                switch (rd.bf) {
                    case 1:
                        bf = 0;
                        rc.bf = -1;
                        break;
                    case 0:
                        bf = rc.bf = 0;
                        break;
                    case -1:
                        bf = 1;
                        rc.bf = 0;
                        break;
                    default:
                        // do nothing
                }
                rd.bf = 0;
                rightChild.rightRotate();
                leftRotate();
                break;
            default:
                // do nothing
        }

        return shorter;
    }

    public void rightRotate() {
        BalancedBinaryTree rc = new BalancedBinaryTree();
        rc.insert(root.key);
        rc.bf = bf;
        rc.rightChild = rightChild;
        rc.leftChild = leftChild.rightChild;
        root = new Node(leftChild.root.key);
        bf = leftChild.bf;
        rightChild = rc;
        leftChild = leftChild.leftChild;
    }

    public void leftRotate() {
        BalancedBinaryTree rc = new BalancedBinaryTree();
        rc.insert(root.key);
        rc.bf = bf;
        rc.leftChild = leftChild;
        rc.rightChild = rightChild.leftChild;
        root = new Node(rightChild.root.key);
        bf = rightChild.bf;
        leftChild = rc;
        rightChild = rightChild.rightChild;

    }

    public DeleteResult delete(int key) {
        boolean deleted = false;
        boolean shorter = false;
        int keyDeleted = 0;
        if (root != null && root.key == key) {
            deleted = true;
            keyDeleted = root.key;
            DeleteResult result = null;
            switch (bf) {
                case 1:
                    result = leftChild.deleteMax();

                    if (leftChild.root == null) {
                        leftChild = null;
                    }

                    if (result.shorter) {
                        bf = 0;
                        shorter = true;
                    }
                    root = new Node(result.key);
                    break;
                case 0:
                    if (leftChild != null) {
                        result = leftChild.deleteMax();

                        if (leftChild.root == null) {
                            leftChild = null;
                        }

                        if (result.shorter) {
                            bf = -1;
                        }
                        root = new Node(result.key);
                    } else {
                        root = null;
                        shorter = true;
                    }
                    break;
                case -1:
                    result = rightChild.deleteMin();

                    if (rightChild.root == null) {
                        rightChild = null;
                    }

                    if (result.shorter) {
                        bf = 0;
                        shorter = true;
                    }
                    root = new Node(result.key);
                    break;
                default:
                    // do nothing
            }
        } else if (key < root.key && leftChild != null) {
            DeleteResult result = leftChild.delete(key);

            if (leftChild.root == null) {
                leftChild = null;
            }

            if (result.deleted && result.shorter) {
                deleted = true;
                keyDeleted = result.key;
                switch (bf) {
                    case 1:
                        bf = 0;
                        shorter = true;
                        break;
                    case 0:
                        bf = -1;
                        break;
                    case -1:
                        shorter = rightBalance();
                        break;
                    default:
                        // do nothing.
                }
            }
        } else if (key > root.key && rightChild != null) {
            DeleteResult result = rightChild.delete(key);

            if (rightChild.root == null) {
                rightChild = null;
            }

            if (result.deleted && result.shorter) {
                deleted = true;
                keyDeleted = result.key;
                switch (bf) {
                    case 1:
                        shorter = leftBalance();
                        break;
                    case 0:
                        bf = 1;
                        break;
                    case -1:
                        bf = 0;
                        shorter = true;
                        break;
                    default:
                        // do nothing.
                }
            }
        }

        return new DeleteResult(deleted, shorter, keyDeleted);
    }

    public DeleteResult deleteMax() {
        boolean shorter = false;
        int keyDeleted = 0;
        if (rightChild != null) {
            DeleteResult result = rightChild.deleteMax();

            if (rightChild.root == null) {
                rightChild = null;
            }

            if (result.shorter) {
                switch (bf) {
                    case 1:
                        shorter = leftBalance();
                        break;
                    case 0:
                        bf = 1;
                        break;
                    case -1:
                        bf = 0;
                        shorter = true;
                        break;
                    default:
                        // do nothing.
                }
            }

            keyDeleted = result.key;
        } else {
            keyDeleted = root.key;
            shorter = true;
            if (leftChild == null) {
                root = null;
            } else {
                BalancedBinaryTree lc = leftChild;
                root = lc.root;
                bf = lc.bf;
                leftChild = lc.leftChild;
                rightChild = lc.rightChild;
            }
        }
        return new DeleteResult(true, shorter, keyDeleted);
    }

    public DeleteResult deleteMin() {
        boolean shorter = false;
        int keyDeleted = 0;
        if (leftChild != null) {
            DeleteResult result = leftChild.deleteMin();
            if (leftChild.root == null) {
                leftChild = null;
            }

            if (result.shorter) {
                switch (bf) {
                    case 1:
                        bf = 0;
                        shorter = true;
                        break;
                    case 0:
                        bf = -1;
                        break;
                    case -1:
                        shorter = rightBalance();
                        break;
                    default:
                        // do nothing.
                }
            }

            keyDeleted = result.key;
        } else {
            keyDeleted = root.key;
            shorter = true;
            if (rightChild == null) {
                root = null;
            } else {
                BalancedBinaryTree rc = rightChild;
                root = rc.root;
                bf = rc.bf;
                leftChild = rc.leftChild;
                rightChild = rc.rightChild;
            }
        }
        return new DeleteResult(true, shorter, keyDeleted);
    }


    private boolean any(BalancedBinaryTree[] array) {
        for (BalancedBinaryTree tree : array) {
            if (tree != null) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        //return stringfyTree(this);
        return  toString2();
    }

    public String toString2() {

        final int W = 2;
        final int H = 5;
        int level = 1;

        StringBuilder sb = new StringBuilder();
        BalancedBinaryTree[] array = new BalancedBinaryTree[]{this};

        while (any(array)) {
            BalancedBinaryTree[] cArray = new BalancedBinaryTree[2 * array.length];
            appendBlanks(sb, ((W + 2) * (2 << (H - level))) - 1 - 2);
            for (int i = 0; i < array.length; i++) {
                if (array[i] != null) {
                    cArray[2 * i] = array[i].leftChild;
                    cArray[2 * i + 1] = array[i].rightChild;
                    sb.append(stringfyNode(array[i].root));
                } else {
                    appendBlanks(sb,W);
                }

                if (i < array.length - 1) {
                    appendBlanks(sb, (W + 2) * (2 << (H - level + 1)) - W);
                }
            }
            sb.append('\n');

            array = cArray;
            level++;

            if (any(array)) {
                appendBlanks(sb, ((W + 2) * (2 << (H - level))) - 1 - 2);
                for (int i = 0; i < array.length; i++) {
                    if (array[i] != null) {
                        if (i % 2 == 0) {
                            sb.append(" /");
                        } else {
                            sb.append("\\ ");
                        }
                    } else {
                        appendBlanks(sb,W);
                    }

                    if (i < array.length - 1) {
                        appendBlanks(sb, (W + 2) * (2 << (H - level + 1)) - W);
                    }
                }
                sb.append('\n');
            }
        }

        sb.append("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n");
        return sb.toString();
    }

    void appendBlanks(StringBuilder sb, int count) {
        for (int i = 0; i < count; i++) {
            sb.append(' ');
        }
    }

    void printBlanks(int count) {
        for (int i = 0; i < count; i++) {
            System.out.print(' ');
        }
    }

    String stringfyNode(Node node) {
        if (node == null) {
            return "  ";
        } else {
            return String.format("%1$02d", node.key);
        }
    }


    String stringfyTree(BalancedBinaryTree t) {
        if (t == null || t.root == null) {
            return "";
        } else {
            return  (t.leftChild != null ? "(" + stringfyTree(t.leftChild) + ") " : "") + t.root.key
                    + (t.rightChild != null ? " (" + stringfyTree(t.rightChild) + ")" : "");
        }
    }

    public static void main(String[] args) {
        int[] src = new int[] {5,13,12,1,8,7,10,15,3,9,2,4,6,14,11,16,17,18,19,20,21,22,23,24,25,26,0,-1, -2, -3, -4, -5, -6, -7, -8, -9};
        //int[] src = new int[] {5,13,12,1,8,7,10,15,3,9,2,4,6,14,11,16,17,18,19,20,21,22,23,24,25,26,0};
        //int[] src = new int[] {5,13,12,1,8,7,10,15};
        BalancedBinaryTree bbt = new BalancedBinaryTree();

        for (int i = 0; i < src.length; i++) {
            System.out.println("Inserting " + src[i]);
            bbt.insert(src[i]);
            System.out.println(bbt);
        }

        for (int i = 0; i < src.length; i++) {
            System.out.println("Deleting " + src[i]);
            bbt.delete(src[i]);
            System.out.println(bbt);
        }
    }
}
