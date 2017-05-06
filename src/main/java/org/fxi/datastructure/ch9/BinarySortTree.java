package org.fxi.datastructure.ch9;

public class BinarySortTree {

    static class Node {
        public final int key;
        public Node leftChild;
        public Node rightChild;

        Node(int key) {
            this.key = key;
        }
    }

    private Node root;
    private int height = 0;

    private BinarySortTree() {
    }

    private BinarySortTree(int[] keys) {
        if (keys != null && keys.length > 0) {
            root = new Node(keys[0]);
            for (int i = 1; i < keys.length; i++) {
                this.insert(keys[i]);
            }
        }
    }

    private void updateHeight(int height) {
        if (height > this.height) {
            this.height = height;
        }
    }

    public BinarySortTree insert(int key) {
        if (root == null) {
            root = new Node(key);
            return this;
        }
        Node current = root;
        boolean done = false;

        while(!done) {
            if (key < current.key) {
                if (current.leftChild == null) {
                    current.leftChild = new Node(key);
                    done = true;
                } else {
                    current = current.leftChild;
                }
            } else if (key > current.key) {
                if (current.rightChild == null) {
                    current.rightChild = new Node(key);
                    done = true;
                } else {
                    current = current.rightChild;
                }
            } else {
                // ignore
                done = true;
            }
        }

        return this;
    }

    private Node deleteNode(Node node) {
        Node selectedParent = null;
        Node selected = null;
        if (node.leftChild != null) {
            selected = node.leftChild;
            while (selected.rightChild != null) {
                selectedParent = selected;
                selected = selected.rightChild;
            }

            if (selectedParent != null) {
                selectedParent.rightChild = selected.leftChild;
                selected.leftChild = node.leftChild;
            }
        } else if (node.rightChild != null) {
            selected = node.rightChild;
            while (selected.leftChild != null) {
                selectedParent = selected;
                selected = selected.leftChild;
            }

            if (selectedParent != null) {
                selectedParent.leftChild = selected.rightChild;
                selected.rightChild = node.rightChild;
            }
        }

        return selected;
    }

    private Node deleteNode2(Node node) {
        Node selected = null;
        if (node.leftChild != null) {
            selected = node.leftChild;
            Node a = selected;
            while(a.rightChild != null) {
                a = a.rightChild;
            }
            a.rightChild = node.rightChild;
        } else if (node.rightChild != null) {
            selected = node.rightChild;
            Node b = selected;

            while (b.leftChild != null) {
                b = b.leftChild;
            }
            b.leftChild = node.leftChild;
        }

        return selected;
    }

    public BinarySortTree delete(int key) {
        Node toDeleteParent = null;
        Node toDelete = root;
        boolean found = false;
        int child = 0;
        while(toDelete != null && !found) {
            if (key > toDelete.key) {
                toDeleteParent = toDelete;
                toDelete = toDelete.rightChild;
                child = 1;
            } else if (key < toDelete.key) {
                toDeleteParent = toDelete;
                toDelete = toDelete.leftChild;
                child = -1;
            } else {
                found = true;
                //Node remaining = deleteNode(toDelete);
                Node remaining = deleteNode2(toDelete);
                if (toDeleteParent == null) {
                    root = remaining;
                } else {
                    if (remaining != null) {
                        if (remaining.key > toDeleteParent.key) {
                            toDeleteParent.rightChild = remaining;
                        } else {
                            toDeleteParent.leftChild = remaining;
                        }
                    } else {
                        if (child > 0) {
                            toDeleteParent.rightChild = null;
                        } else if (child < 0) {
                            toDeleteParent.leftChild = null;
                        }
                    }
                }
            }
        }

        return this;
    }

    @Override
    public String toString() {
        if (root == null) {
            return "()";
        } else {
            StringBuilder sb = new StringBuilder();

        }

        return "(" + stringfyNode(root) + ")";
    }

    String stringfyNode(Node node) {
        if (node == null) {
            return "";
        } else {
            return  (node.leftChild != null ? "(" + stringfyNode(node.leftChild) + ") " : "") + node.key
                    + (node.rightChild != null ? " (" + stringfyNode(node.rightChild) + ")" : "");
        }
    }

    public static void main(String[] args) {
        int[] src = new int[] {5,13,12,1,8,7,10,15,3,9,2,4,6,14,11};
        BinarySortTree bst = new BinarySortTree();

        for (int i = 0; i < src.length; i++) {
            bst.insert(src[i]);
            System.out.println(bst);
        }

        for (int i = 1; i <= 15; i++) {
            bst.delete(i);
            System.out.println(bst);
        }
    }
}
