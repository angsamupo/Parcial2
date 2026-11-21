package rojonegro;

/**
 * Ejemplo independiente para entender las rotaciones.
 * Usa un ABB simple (sin colores) para ver solo la reestructuración.
 *
 * Idea clave: las rotaciones cambian la forma del árbol,
 * pero el recorrido inorden (el orden de los valores) NO cambia.
 */
public class Rotaciones {

    static class Node {
        int key;
        Node left, right;

        Node(int key) {
            this.key = key;
        }
    }

    /** Rotación a la izquierda: el hijo derecho sube. Devuelve la nueva raíz del subárbol. */
    static Node rotateLeft(Node x) {
        Node y = x.right;
        x.right = y.left;  // el subárbol del medio cambia de padre
        y.left = x;        // x baja como hijo izquierdo de y
        return y;
    }

    /** Rotación a la derecha: el hijo izquierdo sube. Devuelve la nueva raíz del subárbol. */
    static Node rotateRight(Node y) {
        Node x = y.left;
        y.left = x.right;  // el subárbol del medio cambia de padre
        x.right = y;       // y baja como hijo derecho de x
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

    /** Imprime el árbol de lado (raíz a la izquierda, hijo derecho arriba). */
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
        // Ejemplo 1: cadena 10 -> 20 -> 30 (el caso clásico que necesita una rotación)
        Node root = new Node(10);
        root.right = new Node(20);
        root.right.right = new Node(30);

        show("Ejemplo 1 - Antes (cadena hacia la derecha)", root);
        root = rotateLeft(root);
        show("Ejemplo 1 - Después de rotar a la izquierda en 10", root);

        // Ejemplo 2: rotación con subárbol "del medio" (el que cambia de padre)
        //          10                     20
        //         /  \                   /  \
        //        5    20      -->      10    30
        //            /  \             /  \
        //          15    30          5    15
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
