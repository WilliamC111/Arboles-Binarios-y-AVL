class Tree {
    int key, height;
    Tree left, right;

    Tree(int key) {
        this.key = key;
        this.height = 1;
    }
}

class AVLTree {
    Tree root;

    int height(Tree tree) {
        if (tree == null)
            return 0;
        return tree.height;
    }

    int balanceFactor(Tree tree) {
        if (tree == null)
            return 0;
        return height(tree.left) - height(tree.right);
    }

    Tree rightRotate(Tree y) {
        Tree x = y.left;
        Tree T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    Tree leftRotate(Tree x) {
        Tree y = x.right;
        Tree T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    Tree minValueNode(Tree node) {
        Tree current = node;
        while (current.left != null)
            current = current.left;
        return current;
    }

    Tree deleteNode(Tree root, int key) {
        if (root == null)
            return root;

        if (key < root.key)
            root.left = deleteNode(root.left, key);
        else if (key > root.key)
            root.right = deleteNode(root.right, key);
        else {
            if ((root.left == null) || (root.right == null)) {
                Tree temp = null;
                if (temp == root.left)
                    temp = root.right;
                else
                    temp = root.left;

                if (temp == null) {
                    temp = root;
                    root = null;
                } else
                    root = temp;
            } else {
                Tree temp = minValueNode(root.right);

                root.key = temp.key;

                root.right = deleteNode(root.right, temp.key);
            }
        }

        if (root == null)
            return root;

        root.height = Math.max(height(root.left), height(root.right)) + 1;

        int balance = balanceFactor(root);

    
        if (balance > 1) {
            if (balanceFactor(root.left) >= 0)
                return rightRotate(root);
            else {
                root.left = leftRotate(root.left);
                return rightRotate(root);
            }
        }
        if (balance < -1) {
            if (balanceFactor(root.right) <= 0)
                return leftRotate(root);
            else {
                root.right = rightRotate(root.right);
                return leftRotate(root);
            }
        }

        return root;
    }

    void delete(int key) {
        root = deleteNode(root, key);
    }
    Tree insert(Tree node, int key) {
        if (node == null)
            return new Tree(key);

        if (key < node.key)
            node.left = insert(node.left, key);
        else if (key > node.key)
            node.right = insert(node.right, key);
        else
            return node;

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = balanceFactor(node);

             if (balance > 1) {
            if (key < node.left.key)
                return rightRotate(node);
            else {
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }
        }
        if (balance < -1) {
            if (key > node.right.key)
                return leftRotate(node);
            else {
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }
        }

        return node;
    }

    void insert(int key) {
        root = insert(root, key);
    }

    
    boolean isBalanced(Tree tree) {
        if (tree == null)
            return true;

        int balance = balanceFactor(tree);

        if (Math.abs(balance) > 1)
            return false;

        return isBalanced(tree.left) && isBalanced(tree.right);
    }


    void inOrder(Tree tree) {
        if (tree != null) {
            inOrder(tree.left);
            System.out.print(tree.key + " ");
            inOrder(tree.right);
        }
    }

    public static void main(String[] args) {
        AVLTree tree = new AVLTree();

        tree.insert(10);
        tree.insert(20);
        tree.insert(30);

        System.out.println("Recorrido Inorden antes de la eliminación:");
        tree.inOrder(tree.root);

        tree.delete(20);

        System.out.println("\nRecorrido Inorden después de la eliminación:");
        tree.inOrder(tree.root);

        if (tree.isBalanced(tree.root))
            System.out.println("\nEl árbol está balanceado.");
        else
            System.out.println("\nEl árbol no está balanceado.");
    }
}
