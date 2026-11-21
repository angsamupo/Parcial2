package rojonegro;

public class Main {

    public static void main(String[] args) {
        ejemploInsercion();
        ejemploBusqueda();
        ejemploAlturaNegra();
        ejemploBalanceo();
    }

    static void ejemploInsercion() {
        System.out.println("===== 1. Inserción paso a paso =====");
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        int[] valores = {10, 20, 30, 15, 25};

        for (int v : valores) {
            tree.insert(v);
            System.out.println("Después de insertar " + v + ":");
            tree.print();
            System.out.println();
        }
    }

    static void ejemploBusqueda() {
        System.out.println("===== 2. Búsqueda =====");
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        for (int v : new int[]{10, 20, 30, 15, 25}) {
            tree.insert(v);
        }

        System.out.println("¿Contiene 25? " + tree.contains(25));
        System.out.println("Camino al buscar 25: " + tree.searchPath(25));
        System.out.println("¿Contiene 99? " + tree.contains(99));
        System.out.println("Camino al buscar 99: " + tree.searchPath(99));
        System.out.println();
    }

    static void ejemploAlturaNegra() {
        System.out.println("===== 3. Altura negra y validación =====");
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        for (int v : new int[]{10, 20, 30, 15, 25}) {
            tree.insert(v);
        }

        System.out.println("Recorrido inorden: " + tree.inOrder());
        System.out.println("Altura del árbol: " + tree.height());
        System.out.println("Altura negra: " + tree.blackHeight());
        System.out.println("¿Cumple las propiedades? " + tree.isValid());
        System.out.println();
    }

    static void ejemploBalanceo() {
        System.out.println("===== 4. Balanceo con inserciones ordenadas =====");
        System.out.println("n\tAltura rojo-negro\tAltura ABB sin balanceo\t¿Válido?");
        for (int n : new int[]{7, 15, 31, 63, 127, 1000}) {
            RedBlackTree<Integer> tree = new RedBlackTree<>();
            for (int i = 1; i <= n; i++) {
                tree.insert(i);
            }
            System.out.println(n + "\t" + tree.height() + "\t\t\t" + (n - 1) + "\t\t\t" + tree.isValid());
        }
    }
}
