class Node {
    int key, height;
    Node left, right;

    Node(int key) {
        this.key = key;
        this.height = 1;
    }
}

class AVLTree {
    Node root;

    int height(Node node) {
        if (node == null)
            return 0;
        return node.height;
    }

    int balanceFactor(Node node) {
        if (node == null)
            return 0;
        return height(node.left) - height(node.right);
    }

    Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    Node insert(Node node, int key) {
        if (node == null)
            return new Node(key);

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

    
    boolean isBalanced(Node node) {
        if (node == null)
            return true;

        boolean leftBalanced = isBalanced(node.left);
        boolean rightBalanced = isBalanced(node.right);

        int balance = balanceFactor(node);

        if (Math.abs(balance) > 1)
            return false;

        return leftBalanced && rightBalanced;
    }

 
    void postOrder(Node node) {
        if (node != null) {
            postOrder(node.left);
            postOrder(node.right);
            System.out.print(node.key + " ");
        }
    }

    public static void main(String[] args) {
        AVLTree tree = new AVLTree();

        tree.insert(10);
        tree.insert(20);
        tree.insert(30);
        tree.insert(50);

        System.out.println("Recorrido Postorder del Árbol:");
        tree.postOrder(tree.root);

        if (tree.isBalanced(tree.root))
            System.out.println("\nEl árbol está balanceado.");
        else
            System.out.println("\nEl árbol no está balanceado.");
    }
}