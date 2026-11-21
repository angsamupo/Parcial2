package rojonegro;


public class Rotaciones {

    static class Node {
        int key;
        Node left, right;

        Node(int key) {
            this.key = key;
        }
    }

    static Node rotateLeft(Node x) {
        Node y = x.right;
        x.right = y.left;  
        y.left = x;        
        return y;
    }

    static Node rotateRight(Node y) {
        Node x = y.left;
        y.left = x.right; 
        x.right = y;       
        return x;
    }

    static void inOrder(Node n, StringBuilder sb) {
        if (n == null) {
            return;
        }
        inOrder(n.left, sb);
        sb.append(n.key).append(' ');
        inOrder(n.right, sb);
    }

    static void preOrder(Node n, StringBuilder sb) {
        if (n == null) {
            return;
        }
        sb.append(n.key).append(' ');
        preOrder(n.left, sb);
        preOrder(n.right, sb);
    }

    static void print(Node n, int depth) {
        if (n == null) {
            return;
        }
        print(n.right, depth + 1);
        System.out.println("    ".repeat(depth) + n.key);
        print(n.left, depth + 1);
    }

    static void show(String title, Node root) {
        StringBuilder in = new StringBuilder();
        StringBuilder pre = new StringBuilder();
        inOrder(root, in);
        preOrder(root, pre);
        System.out.println("--- " + title + " ---");
        print(root, 0);
        System.out.println("Inorden:  " + in);
        System.out.println("Preorden: " + pre);
        System.out.println();
    }

    public static void main(String[] args) {
        Node root = new Node(10);
        root.right = new Node(20);
        root.right.right = new Node(30);

        show("Ejemplo 1 - Antes (cadena hacia la derecha)", root);
        root = rotateLeft(root);
        show("Ejemplo 1 - Después de rotar a la izquierda en 10", root);

        Node x = new Node(10);
        x.left = new Node(5);
        x.right = new Node(20);
        x.right.left = new Node(15);
        x.right.right = new Node(30);

        show("Ejemplo 2 - Antes", x);
        x = rotateLeft(x);
        show("Ejemplo 2 - Después de rotar a la izquierda en 10", x);
        x = rotateRight(x);
        show("Ejemplo 2 - Después de rotar a la derecha en 20 (vuelve al original)", x);
    }
}
