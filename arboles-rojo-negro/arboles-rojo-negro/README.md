# Árboles Rojo-Negro en Java

Ejemplos en Java para la presentación de Ciencias de la Computación sobre árboles rojo-negro.

## Contenido

| Archivo | Qué muestra |
|---|---|
| `src/rojonegro/RedBlackTree.java` | Implementación del árbol: búsqueda, inserción, rotaciones, altura negra y validación de propiedades |
| `src/rojonegro/Rotaciones.java` | Rotaciones izquierda y derecha sobre un ABB simple, comprobando que el orden inorden no cambia |
| `src/rojonegro/Main.java` | Demostraciones: inserción paso a paso, búsqueda, altura negra y comparación de alturas |

## Propiedades de un árbol rojo-negro

1. Cada nodo es rojo o negro.
2. La raíz es negra.
3. Las hojas (NIL) son negras.
4. Un nodo rojo no tiene hijos rojos.
5. Todos los caminos de un nodo a sus hojas tienen la misma cantidad de nodos negros (altura negra).

## Cómo compilar y ejecutar

Requiere JDK 11 o superior.

```bash
# Compilar
javac -d out src/rojonegro/*.java

# Ejecutar la demostración general
java -cp out rojonegro.Main

# Ejecutar el ejemplo de rotaciones
java -cp out rojonegro.Rotaciones
```

Si las tildes se ven raras en Windows, ejecuta antes `chcp 65001`
o agrega `-Dfile.encoding=UTF-8` al comando `java`.

## Cómo leer el árbol impreso

El árbol se imprime "de lado": la raíz queda a la izquierda, el hijo derecho
arriba y el hijo izquierdo abajo. `(R)` = nodo rojo, `(N)` = nodo negro.

```
    30(N)
        25(R)
20(N)
        15(R)
    10(N)
```

## Complejidad

| Operación | Costo |
|---|---|
| Búsqueda | O(log n) |
| Inserción | O(log n) |
| Rotación | O(1) |

## Subir el proyecto a GitHub

```bash
git init
git add .
git commit -m "Ejemplos de árboles rojo-negro en Java"
git branch -M main
git remote add origin https://github.com/TU_USUARIO/arboles-rojo-negro.git
git push -u origin main
```
