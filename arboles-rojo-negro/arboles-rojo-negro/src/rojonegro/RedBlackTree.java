package rojonegro;

import java.util.ArrayList;
import java.util.List;

/**
 * Árbol rojo-negro (versión estilo CLRS, con un nodo centinela NIL).
 *
 * Propiedades que se mantienen:
 *  1. Cada nodo es rojo o negro.
 *  2. La raíz es negra.
 *  3. Las hojas (NIL) son negras.
 *  4. Un nodo rojo no puede tener hijos rojos.
 *  5. Todos los caminos de un nodo a sus hojas tienen la misma cantidad de nodos negros.
 *
 * Operaciones incluidas: búsqueda, inserción, rotaciones, altura negra y validación.
 * (La eliminación no está incluida.)
 */
public class RedBlackTree<T extends Comparable<T>> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node {
        T key;
        Node left, right, parent;
        boolean color;

        Node(T key, boolean color) {
            this.key = key;
            this.color = color;
        }
    }

    private final Node nil; // centinela: representa todas las hojas, siempre negro
    private Node root;
    private int size;

    public RedBlackTree() {
        nil = new Node(null, BLACK);
        nil.left = nil;
        nil.right = nil;
        nil.parent = nil;
        root = nil;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return root == nil;
    }

    // ------------------------------------------------------------------
    // BÚSQUEDA: igual que en un ABB, el color no influye. O(log n)
    // ------------------------------------------------------------------

    public boolean contains(T key) {
        return find(key) != nil;
    }

    /** Devuelve las claves visitadas al buscar 'key' (útil para mostrar el camino). */
    public List<T> searchPath(T key) {
        List<T> path = new ArrayList<>();
        Node current = root;
        while (current != nil) {
            path.add(current.key);
            int cmp = key.compareTo(current.key);
            if (cmp == 0) {
                break;
            }
            current = (cmp < 0) ? current.left : current.right;
        }
        return path;
    }

    private Node find(T key) {
        Node current = root;
        while (current != nil) {
            int cmp = key.compareTo(current.key);
            if (cmp == 0) {
                return current;
            }
            current = (cmp < 0) ? current.left : current.right;
        }
        return nil;
    }

    // ------------------------------------------------------------------
    // ROTACIONES: reestructuran el árbol sin alterar el orden. O(1)
    // ------------------------------------------------------------------

    /**
     * Rotación a la izquierda sobre x:
     *
     *      x                y
     *     / \              / \
     *    a   y     -->    x   c
     *       / \          / \
     *      b   c        a   b
     */
    private void rotateLeft(Node x) {
        Node y = x.right;          // y sube
        x.right = y.left;          // el subárbol b pasa a ser hijo derecho de x
        if (y.left != nil) {
            y.left.parent = x;
        }
        y.parent = x.parent;       // y toma el lugar de x
        if (x.parent == nil) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;                // x baja como hijo izquierdo de y
        x.parent = y;
    }

    /**
     * Rotación a la derecha sobre y (imagen en espejo de la anterior):
     *
     *        y            x
     *       / \          / \
     *      x   c  -->   a   y
     *     / \              / \
     *    a   b            b   c
     */
    private void rotateRight(Node y) {
        Node x = y.left;           // x sube
        y.left = x.right;          // el subárbol b pasa a ser hijo izquierdo de y
        if (x.right != nil) {
            x.right.parent = y;
        }
        x.parent = y.parent;       // x toma el lugar de y
        if (y.parent == nil) {
            root = x;
        } else if (y == y.parent.right) {
            y.parent.right = x;
        } else {
            y.parent.left = x;
        }
        x.right = y;               // y baja como hijo derecho de x
        y.parent = x;
    }

    // ------------------------------------------------------------------
    // INSERCIÓN: como en un ABB, pintando el nodo de rojo, y luego se corrige
    // ------------------------------------------------------------------

    /** Inserta la clave. Devuelve false si ya existía (no se permiten duplicados). */
    public boolean insert(T key) {
        Node parent = nil;
        Node current = root;
        while (current != nil) {
            parent = current;
            int cmp = key.compareTo(current.key);
            if (cmp == 0) {
                return false;
            }
            current = (cmp < 0) ? current.left : current.right;
        }

        Node z = new Node(key, RED);
        z.left = nil;
        z.right = nil;
        z.parent = parent;

        if (parent == nil) {
            root = z;
        } else if (key.compareTo(parent.key) < 0) {
            parent.left = z;
        } else {
            parent.right = z;
        }
        size++;

        insertFixup(z);
        return true;
    }

    private void insertFixup(Node z) {
        while (z.parent.color == RED) {
            if (z.parent == z.parent.parent.left) {
                Node uncle = z.parent.parent.right;
                if (uncle.color == RED) {
                    // Caso 1: tío rojo -> recolorear y subir el problema
                    z.parent.color = BLACK;
                    uncle.color = BLACK;
                    z.parent.parent.color = RED;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.right) {
                        // Caso 2: tío negro, forma de "triángulo" -> rotar para dejarlo en línea
                        z = z.parent;
                        rotateLeft(z);
                    }
                    // Caso 3: tío negro, forma de "línea" -> recolorear y rotar
                    z.parent.color = BLACK;
                    z.parent.parent.color = RED;
                    rotateRight(z.parent.parent);
                }
            } else {
                // Casos simétricos (el padre es hijo derecho)
                Node uncle = z.parent.parent.left;
                if (uncle.color == RED) {
                    z.parent.color = BLACK;
                    uncle.color = BLACK;
                    z.parent.parent.color = RED;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.left) {
                        z = z.parent;
                        rotateRight(z);
                    }
                    z.parent.color = BLACK;
                    z.parent.parent.color = RED;
                    rotateLeft(z.parent.parent);
                }
            }
        }
        root.color = BLACK; // la raíz siempre es negra
    }

    // ------------------------------------------------------------------
    // ALTURA Y ALTURA NEGRA
    // ------------------------------------------------------------------

    /** Altura del árbol (número de aristas del camino más largo desde la raíz). */
    public int height() {
        return height(root);
    }

    private int height(Node n) {
        if (n == nil) {
            return -1;
        }
        return 1 + Math.max(height(n.left), height(n.right));
    }

    /**
     * Altura negra de la raíz: número de nodos negros en cualquier camino desde la raíz
     * hasta una hoja, sin contar la raíz e incluyendo la hoja NIL (definición de CLRS).
     */
    public int blackHeight() {
        int bh = 0;
        Node n = root;
        while (n != nil) {
            n = n.left;
            if (n.color == BLACK) {
                bh++;
            }
        }
        return bh;
    }

    // ------------------------------------------------------------------
    // VALIDACIÓN: comprueba las propiedades del árbol
    // ------------------------------------------------------------------

    public boolean isValid() {
        if (root.color != BLACK) {
            return false;
        }
        return check(root, null, null) != -1;
    }

    /** Devuelve la altura negra del subárbol, o -1 si se viola alguna propiedad. */
    private int check(Node n, T min, T max) {
        if (n == nil) {
            return 1;
        }
        if (min != null && n.key.compareTo(min) <= 0) {
            return -1; // viola el orden del ABB
        }
        if (max != null && n.key.compareTo(max) >= 0) {
            return -1;
        }
        if (n.color == RED && (n.left.color == RED || n.right.color == RED)) {
            return -1; // dos rojos seguidos
        }
        int left = check(n.left, min, n.key);
        int right = check(n.right, n.key, max);
        if (left == -1 || right == -1 || left != right) {
            return -1; // distinta altura negra
        }
        return left + (n.color == BLACK ? 1 : 0);
    }

    // ------------------------------------------------------------------
    // RECORRIDO E IMPRESIÓN
    // ------------------------------------------------------------------

    public List<T> inOrder() {
        List<T> result = new ArrayList<>();
        inOrder(root, result);
        return result;
    }

    private void inOrder(Node n, List<T> result) {
        if (n == nil) {
            return;
        }
        inOrder(n.left, result);
        result.add(n.key);
        inOrder(n.right, result);
    }

    /**
     * Imprime el árbol "de lado": la raíz queda a la izquierda,
     * el hijo derecho arriba y el hijo izquierdo abajo.
     * (R) = nodo rojo, (N) = nodo negro.
     */
    public void print() {
        if (root == nil) {
            System.out.println("(árbol vacío)");
            return;
        }
        print(root, 0);
    }

    private void print(Node n, int depth) {
        if (n == nil) {
            return;
        }
        print(n.right, depth + 1);
        System.out.println("    ".repeat(depth) + n.key + (n.color == RED ? "(R)" : "(N)"));
        print(n.left, depth + 1);
    }
}
