
// ==========================================
// 1. CLASE NODO
// ==========================================

class Nodo {

    constructor(valor) {

        this.valor = valor;
        this.color = "ROJO";

        this.izquierdo = null;
        this.derecho = null;
        this.padre = null;

    }

}


// ==========================================
// 2. CLASE ARBOL ROJO-NEGRO
// ==========================================

class ArbolRojoNegro {

    constructor() {

        this.raiz = null;

    }


    // --------------------------------------
    // Obtener el color de un nodo
    // null representa una hoja NIL negra
    // --------------------------------------

    colorDe(nodo) {

        return nodo === null ? "NEGRO" : nodo.color;

    }


    // --------------------------------------
    // INSERTAR UN NUEVO VALOR
    // --------------------------------------

    insertar(valor, pasos) {

        let nuevo = new Nodo(valor);

        // Caso: árbol vacío

        if (this.raiz === null) {

            this.raiz = nuevo;
            nuevo.color = "NEGRO";

            pasos.push(
                "El árbol estaba vacío. El nodo " +
                valor + " se convierte en la raíz negra."
            );

            return true;

        }

        let actual = this.raiz;
        let padre = null;

        // Buscar la posición correspondiente

        while (actual !== null) {

            padre = actual;

            if (valor < actual.valor) {

                actual = actual.izquierdo;

            } else if (valor > actual.valor) {

                actual = actual.derecho;

            } else {

                pasos.push(
                    "El valor " + valor +
                    " ya existe. No se permiten duplicados."
                );

                return false;

            }

        }

        // Conectar el nuevo nodo con su padre

        nuevo.padre = padre;

        if (valor < padre.valor) {

            padre.izquierdo = nuevo;

        } else {

            padre.derecho = nuevo;

        }

        pasos.push(
            "Se inserta el nodo " + valor +
            " como hijo de " + padre.valor +
            " y se colorea de rojo."
        );

        // Corregir posibles conflictos

        this.corregirInsercion(nuevo, pasos);

        return true;

    }


    // --------------------------------------
    // 3. ROTACION A LA IZQUIERDA
    // --------------------------------------

    rotarIzquierda(x, pasos) {

        let y = x.derecho;

        if (y === null) return;

        // El hijo izquierdo de y pasa a ser
        // el hijo derecho de x

        x.derecho = y.izquierdo;

        if (y.izquierdo !== null) {

            y.izquierdo.padre = x;

        }

        // Conectar y con el antiguo padre de x

        y.padre = x.padre;

        if (x.padre === null) {

            this.raiz = y;

        } else if (x === x.padre.izquierdo) {

            x.padre.izquierdo = y;

        } else {

            x.padre.derecho = y;

        }

        // x pasa a ser hijo izquierdo de y

        y.izquierdo = x;
        x.padre = y;

        pasos.push(
            "Rotación a la izquierda sobre el nodo " +
            x.valor + ". El nodo " + y.valor +
            " sube y el nodo " + x.valor + " baja."
        );

    }


    // --------------------------------------
    // 4. ROTACION A LA DERECHA
    // --------------------------------------

    rotarDerecha(y, pasos) {

        let x = y.izquierdo;

        if (x === null) return;

        // El hijo derecho de x pasa a ser
        // el hijo izquierdo de y

        y.izquierdo = x.derecho;

        if (x.derecho !== null) {

            x.derecho.padre = y;

        }

        // Conectar x con el antiguo padre de y

        x.padre = y.padre;

        if (y.padre === null) {

            this.raiz = x;

        } else if (y === y.padre.izquierdo) {

            y.padre.izquierdo = x;

        } else {

            y.padre.derecho = x;

        }

        // y pasa a ser hijo derecho de x

        x.derecho = y;
        y.padre = x;

        pasos.push(
            "Rotación a la derecha sobre el nodo " +
            y.valor + ". El nodo " + x.valor +
            " sube y el nodo " + y.valor + " baja."
        );

    }


    // --------------------------------------
    // 5. CORREGIR LA INSERCION
    // --------------------------------------

    corregirInsercion(nodo, pasos) {

        while (
            nodo !== this.raiz &&
            this.colorDe(nodo.padre) === "ROJO"
        ) {

            let padre = nodo.padre;
            let abuelo = padre.padre;

            // CASO: el padre está a la izquierda

            if (padre === abuelo.izquierdo) {

                let tio = abuelo.derecho;

                // CASO 1: TIO ROJO

                if (this.colorDe(tio) === "ROJO") {

                    pasos.push(
                        "Caso 1: el padre y el tío son rojos. " +
                        "Se recolorean ambos a negro y el abuelo a rojo."
                    );

                    padre.color = "NEGRO";
                    tio.color = "NEGRO";
                    abuelo.color = "ROJO";

                    nodo = abuelo;

                } else {

                    // CASO 2: IZQUIERDA - DERECHA

                    if (nodo === padre.derecho) {

                        pasos.push(
                            "Caso 2: configuración izquierda-derecha (LR). " +
                            "Primero se rota a la izquierda sobre el padre."
                        );

                        nodo = padre;

                        this.rotarIzquierda(nodo, pasos);

                    }

                    // CASO 3: IZQUIERDA - IZQUIERDA

                    pasos.push(
                        "Caso 3: configuración izquierda-izquierda (LL). " +
                        "Se recolorean los nodos y se rota a la derecha sobre el abuelo."
                    );

                    nodo.padre.color = "NEGRO";
                    nodo.padre.padre.color = "ROJO";

                    this.rotarDerecha(nodo.padre.padre, pasos);

                }

            } else {

                // CASOS SIMETRICOS:
                // EL PADRE ESTA A LA DERECHA

                let tio = abuelo.izquierdo;

                // CASO 1: TIO ROJO

                if (this.colorDe(tio) === "ROJO") {

                    pasos.push(
                        "Caso 1 simétrico: el padre y el tío son rojos. " +
                        "Se recolorean ambos a negro y el abuelo a rojo."
                    );

                    padre.color = "NEGRO";
                    tio.color = "NEGRO";
                    abuelo.color = "ROJO";

                    nodo = abuelo;

                } else {

                    // CASO 2: DERECHA - IZQUIERDA

                    if (nodo === padre.izquierdo) {

                        pasos.push(
                            "Caso 2: configuración derecha-izquierda (RL). " +
                            "Primero se rota a la derecha sobre el padre."
                        );

                        nodo = padre;

                        this.rotarDerecha(nodo, pasos);

                    }

                    // CASO 3: DERECHA - DERECHA

                    pasos.push(
                        "Caso 3: configuración derecha-derecha (RR). " +
                        "Se recolorean los nodos y se rota a la izquierda sobre el abuelo."
                    );

                    nodo.padre.color = "NEGRO";
                    nodo.padre.padre.color = "ROJO";

                    this.rotarIzquierda(nodo.padre.padre, pasos);

                }

            }

        }

        // La raíz siempre debe ser negra

        this.raiz.color = "NEGRO";

        pasos.push(
            "Verificación final: la raíz permanece negra."
        );

    }


    // --------------------------------------
    // 6. CONTAR LOS NODOS
    // --------------------------------------

    contarNodos(nodo = this.raiz) {

        if (nodo === null) return 0;

        return 1 +
            this.contarNodos(nodo.izquierdo) +
            this.contarNodos(nodo.derecho);

    }


    // --------------------------------------
    // 7. VALIDAR PROPIEDADES
    // --------------------------------------

    validarArbol() {

        if (this.raiz === null) {

            return {
                valido: true,
                negros: 1
            };

        }

        if (this.raiz.color !== "NEGRO") {

            return {
                valido: false,
                mensaje: "La raíz no es negra."
            };

        }

        const revisar = (nodo) => {

            if (nodo === null) {

                return {
                    valido: true,
                    negros: 1
                };

            }

            if (
                nodo.color === "ROJO" &&
                (
                    this.colorDe(nodo.izquierdo) === "ROJO" ||
                    this.colorDe(nodo.derecho) === "ROJO"
                )
            ) {

                return {
                    valido: false,
                    mensaje: "Existen dos nodos rojos consecutivos."
                };

            }

            let izquierda = revisar(nodo.izquierdo);
            let derecha = revisar(nodo.derecho);

            if (!izquierda.valido) return izquierda;
            if (!derecha.valido) return derecha;

            if (izquierda.negros !== derecha.negros) {

                return {
                    valido: false,
                    mensaje: "Los caminos tienen distinta cantidad de nodos negros."
                };

            }

            return {
                valido: true,
                negros: izquierda.negros +
                    (nodo.color === "NEGRO" ? 1 : 0)
            };

        };

        return revisar(this.raiz);

    }


    // --------------------------------------
    // 8. REINICIAR EL ARBOL
    // --------------------------------------

    reiniciar() {

        this.raiz = null;

    }

}


// ==========================================
// 9. CREAR EL ARBOL
// ==========================================

const arbol = new ArbolRojoNegro();


// ==========================================
// 10. INSERTAR DESDE LA INTERFAZ
// ==========================================

function insertar() {

    const entrada = document.getElementById("valor");

    const valor = entrada.value.trim();

    const mensaje = document.getElementById("mensaje");

    const pasos = [];

    if (valor === "" || !Number.isSafeInteger(Number(valor))) {

        mensaje.textContent =
            "Por favor, ingresa un número entero válido.";

        return;

    }

    const numero = Number(valor);

    const resultado = arbol.insertar(numero, pasos);

    if (!resultado) {

        mensaje.textContent =
            "No se insertó el valor. Verifica el procedimiento.";

    } else {

        mensaje.textContent =
            "El número " + numero + " fue procesado correctamente.";

    }

    mostrarPasos(pasos);

    dibujarArbol();

    entrada.value = "";
    entrada.focus();

}


// ==========================================
// 11. MOSTRAR LOS PASOS
// ==========================================

function mostrarPasos(pasos) {

    const contenedor = document.getElementById("pasos");

    contenedor.innerHTML = "";

    pasos.forEach((paso, indice) => {

        const elemento = document.createElement("div");

        elemento.className = "paso";

        elemento.textContent =
            (indice + 1) + ". " + paso;

        contenedor.appendChild(elemento);

    });

}


// ==========================================
// 12. DIBUJAR EL ARBOL
// ==========================================

function dibujarArbol() {

    const svg = document.getElementById("lienzo");

    const contador = arbol.contarNodos();

    document.getElementById("contador").textContent =
        "Nodos: " + contador;

    svg.innerHTML = "";

    if (arbol.raiz === null) {

        svg.setAttribute("width", "700");
        svg.setAttribute("height", "200");
        svg.setAttribute("viewBox", "0 0 700 200");

        const texto = document.createElementNS(
            "http://www.w3.org/2000/svg", "text"
        );

        texto.setAttribute("x", "350");
        texto.setAttribute("y", "100");
        texto.setAttribute("text-anchor", "middle");
        texto.setAttribute("font-size", "18");
        texto.setAttribute("fill", "#777");

        texto.textContent = "Árbol vacío";

        svg.appendChild(texto);

        actualizarPropiedades();

        return;

    }

    let posiciones = [];
    let indice = 0;

    // Calcular posiciones mediante recorrido inorden

    function calcularPosiciones(nodo, nivel) {

        if (nodo === null) return;

        calcularPosiciones(nodo.izquierdo, nivel + 1);

        posiciones.push({
            nodo: nodo,
            x: 55 + indice * 90,
            y: 45 + nivel * 90
        });

        indice++;

        calcularPosiciones(nodo.derecho, nivel + 1);

    }

    calcularPosiciones(arbol.raiz, 0);

    const ancho = Math.max(700, indice * 90 + 50);

    let altura = 200;

    posiciones.forEach(posicion => {

        altura = Math.max(altura, posicion.y + 65);

    });

    svg.setAttribute("width", ancho);
    svg.setAttribute("height", altura);
    svg.setAttribute(
        "viewBox",
        "0 0 " + ancho + " " + altura
    );

    const coordenadas = new Map();

    posiciones.forEach(posicion => {

        coordenadas.set(posicion.nodo, posicion);

    });


    // Dibujar primero las conexiones

    posiciones.forEach(posicion => {

        const nodo = posicion.nodo;

        [nodo.izquierdo, nodo.derecho].forEach(hijo => {

            if (hijo !== null) {

                const destino = coordenadas.get(hijo);

                const linea = document.createElementNS(
                    "http://www.w3.org/2000/svg", "line"
                );

                linea.setAttribute("x1", posicion.x);
                linea.setAttribute("y1", posicion.y);
                linea.setAttribute("x2", destino.x);
                linea.setAttribute("y2", destino.y);

                linea.setAttribute("stroke", "#777");
                linea.setAttribute("stroke-width", "2");

                svg.appendChild(linea);

            }

        });

    });


    // Dibujar los nodos

    posiciones.forEach(posicion => {

        const grupo = document.createElementNS(
            "http://www.w3.org/2000/svg", "g"
        );

        const circulo = document.createElementNS(
            "http://www.w3.org/2000/svg", "circle"
        );

        circulo.setAttribute("cx", posicion.x);
        circulo.setAttribute("cy", posicion.y);
        circulo.setAttribute("r", "23");

        circulo.setAttribute(
            "fill",
            posicion.nodo.color === "ROJO"
                ? "#dc2626"
                : "#222222"
        );

        circulo.setAttribute("stroke", "#ffffff");
        circulo.setAttribute("stroke-width", "2");

        const texto = document.createElementNS(
            "http://www.w3.org/2000/svg", "text"
        );

        texto.setAttribute("x", posicion.x);
        texto.setAttribute("y", posicion.y + 5);
        texto.setAttribute("text-anchor", "middle");
        texto.setAttribute("font-size", "14");
        texto.setAttribute("font-weight", "bold");
        texto.setAttribute("fill", "white");

        texto.textContent = posicion.nodo.valor;

        grupo.appendChild(circulo);
        grupo.appendChild(texto);

        svg.appendChild(grupo);

    });

    actualizarPropiedades();

}


// ==========================================
// 13. MOSTRAR LAS PROPIEDADES
// ==========================================

function actualizarPropiedades() {

    const lista = document.getElementById("propiedades");

    lista.innerHTML = "";

    const validacion = arbol.validarArbol();

    const elementos = [

        "Todos los nodos tienen un color rojo o negro.",

        "La raíz es negra.",

        "Los nodos rojos no tienen hijos rojos.",

        "Todos los caminos tienen la misma cantidad de nodos negros."

    ];

    elementos.forEach(texto => {

        const li = document.createElement("li");

        li.textContent = texto;

        lista.appendChild(li);

    });

    const resultado = document.createElement("li");

    if (validacion.valido) {

        resultado.textContent =
            "✓ Todas las propiedades se cumplen.";

        resultado.style.color = "green";

    } else {

        resultado.textContent =
            "✗ Error: " + validacion.mensaje;

        resultado.style.color = "red";

    }

    lista.appendChild(resultado);

}


// ==========================================
// 14. REINICIAR
// ==========================================

function reiniciar() {

    arbol.reiniciar();

    document.getElementById("valor").value = "";

    document.getElementById("mensaje").textContent =
        "El árbol ha sido reiniciado.";

    document.getElementById("pasos").innerHTML =
        "Aquí aparecerán los pasos realizados por el algoritmo.";

    dibujarArbol();

}


// ==========================================
// 15. CARGAR EJEMPLO
// ==========================================

function cargarEjemplo() {

    reiniciar();

    const valores = [30, 20, 10];

    valores.forEach(valor => {

        const pasos = [];

        arbol.insertar(valor, pasos);

        mostrarPasos(pasos);

    });

    document.getElementById("mensaje").textContent =
        "Ejemplo LL cargado: 30, 20, 10.";

    dibujarArbol();

}


// ==========================================
// 16. PERMITIR INSERTAR CON ENTER
// ==========================================

document.getElementById("valor").addEventListener(
    "keydown",
    function(event) {

        if (event.key === "Enter") {

            insertar();

        }

    }
);


// ==========================================
// 17. DIBUJO INICIAL
// ==========================================

dibujarArbol();
