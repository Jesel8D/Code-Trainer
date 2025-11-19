package com.CodeTrainer.codetrainer.data.local

import com.CodeTrainer.codetrainer.data.local.entity.ExerciseEntity
import com.CodeTrainer.codetrainer.data.local.entity.HelpTopicEntity
import com.CodeTrainer.codetrainer.data.local.entity.TipEntity

object DatabaseSeeder {

    // ========== EJERCICIOS DE PYTHON ==========

    fun getPythonExercises(): List<ExerciseEntity> = listOf(
        // BÁSICO - Python (5 ejercicios)
        ExerciseEntity(
            title = "Hola Mundo",
            description = "Escribe un programa que imprima 'Hola Mundo' en la consola.",
            language = "Python",
            level = "Básico",
            solutionCode = "print('Hola Mundo')",
            hint = "Usa la función print() para mostrar texto en pantalla."
        ),
        ExerciseEntity(
            title = "Suma de dos números",
            description = "Crea una función que reciba dos números y retorne su suma.",
            language = "Python",
            level = "Básico",
            solutionCode = "def suma(a, b):\n    return a + b",
            hint = "Define una función con def y usa el operador + para sumar."
        ),
        ExerciseEntity(
            title = "Número par o impar",
            description = "Escribe una función que determine si un número es par o impar.",
            language = "Python",
            level = "Básico",
            solutionCode = "def es_par(n):\n    return n % 2 == 0",
            hint = "Usa el operador módulo % para verificar si el residuo es 0."
        ),
        ExerciseEntity(
            title = "Contador de vocales",
            description = "Crea una función que cuente las vocales en una cadena de texto.",
            language = "Python",
            level = "Básico",
            solutionCode = "def contar_vocales(texto):\n    vocales = 'aeiouAEIOU'\n    return sum(1 for c in texto if c in vocales)",
            hint = "Itera sobre cada carácter y verifica si está en la lista de vocales."
        ),
        ExerciseEntity(
            title = "Lista invertida",
            description = "Escribe una función que invierta una lista de números.",
            language = "Python",
            level = "Básico",
            solutionCode = "def invertir_lista(lista):\n    return lista[::-1]",
            hint = "Puedes usar slicing con [::-1] para invertir una lista."
        ),

        // INTERMEDIO - Python (5 ejercicios)
        ExerciseEntity(
            title = "Fibonacci",
            description = "Implementa una función que retorne los primeros n números de la secuencia de Fibonacci.",
            language = "Python",
            level = "Intermedio",
            solutionCode = "def fibonacci(n):\n    if n <= 0: return []\n    if n == 1: return [0]\n    fib = [0, 1]\n    for i in range(2, n):\n        fib.append(fib[i-1] + fib[i-2])\n    return fib",
            hint = "Cada número es la suma de los dos anteriores. Usa un bucle para generar la secuencia."
        ),
        ExerciseEntity(
            title = "Palíndromo",
            description = "Crea una función que determine si una palabra es un palíndromo.",
            language = "Python",
            level = "Intermedio",
            solutionCode = "def es_palindromo(palabra):\n    palabra = palabra.lower().replace(' ', '')\n    return palabra == palabra[::-1]",
            hint = "Un palíndromo se lee igual al derecho y al revés. Compara la palabra con su versión invertida."
        ),
        ExerciseEntity(
            title = "Ordenamiento burbuja",
            description = "Implementa el algoritmo de ordenamiento burbuja para una lista de números.",
            language = "Python",
            level = "Intermedio",
            solutionCode = "def bubble_sort(lista):\n    n = len(lista)\n    for i in range(n):\n        for j in range(0, n-i-1):\n            if lista[j] > lista[j+1]:\n                lista[j], lista[j+1] = lista[j+1], lista[j]\n    return lista",
            hint = "Compara elementos adyacentes e intercámbialos si están en el orden incorrecto."
        ),
        ExerciseEntity(
            title = "Factorial recursivo",
            description = "Calcula el factorial de un número usando recursión.",
            language = "Python",
            level = "Intermedio",
            solutionCode = "def factorial(n):\n    if n <= 1:\n        return 1\n    return n * factorial(n-1)",
            hint = "El factorial de n es n multiplicado por el factorial de n-1. El caso base es 1."
        ),
        ExerciseEntity(
            title = "Eliminar duplicados",
            description = "Escribe una función que elimine elementos duplicados de una lista manteniendo el orden.",
            language = "Python",
            level = "Intermedio",
            solutionCode = "def eliminar_duplicados(lista):\n    vistos = set()\n    resultado = []\n    for item in lista:\n        if item not in vistos:\n            vistos.add(item)\n            resultado.append(item)\n    return resultado",
            hint = "Usa un set para rastrear elementos ya vistos mientras mantienes el orden original."
        ),

        // AVANZADO - Python (5 ejercicios)
        ExerciseEntity(
            title = "Búsqueda binaria",
            description = "Implementa el algoritmo de búsqueda binaria en una lista ordenada.",
            language = "Python",
            level = "Avanzado",
            solutionCode = "def busqueda_binaria(lista, objetivo):\n    izq, der = 0, len(lista) - 1\n    while izq <= der:\n        mid = (izq + der) // 2\n        if lista[mid] == objetivo:\n            return mid\n        elif lista[mid] < objetivo:\n            izq = mid + 1\n        else:\n            der = mid - 1\n    return -1",
            hint = "Divide el espacio de búsqueda a la mitad en cada iteración comparando con el elemento medio."
        ),
        ExerciseEntity(
            title = "Merge Sort",
            description = "Implementa el algoritmo Merge Sort para ordenar una lista.",
            language = "Python",
            level = "Avanzado",
            solutionCode = "def merge_sort(lista):\n    if len(lista) <= 1:\n        return lista\n    mid = len(lista) // 2\n    izq = merge_sort(lista[:mid])\n    der = merge_sort(lista[mid:])\n    return merge(izq, der)\n\ndef merge(izq, der):\n    resultado = []\n    i = j = 0\n    while i < len(izq) and j < len(der):\n        if izq[i] < der[j]:\n            resultado.append(izq[i])\n            i += 1\n        else:\n            resultado.append(der[j])\n            j += 1\n    resultado.extend(izq[i:])\n    resultado.extend(der[j:])\n    return resultado",
            hint = "Divide la lista en mitades, ordena cada mitad recursivamente y luego combina los resultados."
        ),
        ExerciseEntity(
            title = "Generador de primos",
            description = "Crea un generador que produzca números primos hasta n.",
            language = "Python",
            level = "Avanzado",
            solutionCode = "def generar_primos(n):\n    def es_primo(num):\n        if num < 2:\n            return False\n        for i in range(2, int(num**0.5) + 1):\n            if num % i == 0:\n                return False\n        return True\n    \n    for num in range(2, n + 1):\n        if es_primo(num):\n            yield num",
            hint = "Usa yield para crear un generador. Verifica primalidad dividiendo hasta la raíz cuadrada."
        ),
        ExerciseEntity(
            title = "Decorador de tiempo",
            description = "Implementa un decorador que mida el tiempo de ejecución de una función.",
            language = "Python",
            level = "Avanzado",
            solutionCode = "import time\nfrom functools import wraps\n\ndef medir_tiempo(func):\n    @wraps(func)\n    def wrapper(*args, **kwargs):\n        inicio = time.time()\n        resultado = func(*args, **kwargs)\n        fin = time.time()\n        print(f'{func.__name__} tardó {fin - inicio:.4f} segundos')\n        return resultado\n    return wrapper",
            hint = "Un decorador es una función que envuelve otra función. Usa time.time() antes y después."
        ),
        ExerciseEntity(
            title = "Validador de expresiones",
            description = "Valida que los paréntesis, corchetes y llaves estén balanceados en una expresión.",
            language = "Python",
            level = "Avanzado",
            solutionCode = "def validar_expresion(expresion):\n    pila = []\n    pares = {'(': ')', '[': ']', '{': '}'}\n    for char in expresion:\n        if char in pares:\n            pila.append(char)\n        elif char in pares.values():\n            if not pila or pares[pila.pop()] != char:\n                return False\n    return len(pila) == 0",
            hint = "Usa una pila (stack). Empuja símbolos de apertura y verifica coincidencias al encontrar cierres."
        )
    )

    // ========== EJERCICIOS DE C++ ==========

    fun getCppExercises(): List<ExerciseEntity> = listOf(
        // BÁSICO - C++ (5 ejercicios)
        ExerciseEntity(
            title = "Hola Mundo en C++",
            description = "Escribe un programa que imprima 'Hola Mundo' en la consola usando C++.",
            language = "C++",
            level = "Básico",
            solutionCode = "#include <iostream>\nusing namespace std;\n\nint main() {\n    cout << \"Hola Mundo\" << endl;\n    return 0;\n}",
            hint = "Usa cout para imprimir en consola y no olvides incluir iostream."
        ),
        ExerciseEntity(
            title = "Suma en C++",
            description = "Crea una función que reciba dos números enteros y retorne su suma.",
            language = "C++",
            level = "Básico",
            solutionCode = "#include <iostream>\nusing namespace std;\n\nint suma(int a, int b) {\n    return a + b;\n}\n\nint main() {\n    cout << suma(5, 3) << endl;\n    return 0;\n}",
            hint = "Define una función con tipo de retorno int y parámetros int."
        ),
        ExerciseEntity(
            title = "Mayor de dos números",
            description = "Escribe una función que retorne el mayor de dos números.",
            language = "C++",
            level = "Básico",
            solutionCode = "#include <iostream>\nusing namespace std;\n\nint mayor(int a, int b) {\n    return (a > b) ? a : b;\n}\n\nint main() {\n    cout << mayor(10, 5) << endl;\n    return 0;\n}",
            hint = "Usa el operador ternario ? : o una sentencia if para comparar."
        ),
        ExerciseEntity(
            title = "Tabla de multiplicar",
            description = "Imprime la tabla de multiplicar del número 5 del 1 al 10.",
            language = "C++",
            level = "Básico",
            solutionCode = "#include <iostream>\nusing namespace std;\n\nint main() {\n    for(int i = 1; i <= 10; i++) {\n        cout << \"5 x \" << i << \" = \" << 5*i << endl;\n    }\n    return 0;\n}",
            hint = "Usa un bucle for para iterar del 1 al 10 y multiplica en cada iteración."
        ),
        ExerciseEntity(
            title = "Arreglo de números",
            description = "Declara un arreglo de 5 números enteros e imprime su contenido.",
            language = "C++",
            level = "Básico",
            solutionCode = "#include <iostream>\nusing namespace std;\n\nint main() {\n    int numeros[5] = {1, 2, 3, 4, 5};\n    for(int i = 0; i < 5; i++) {\n        cout << numeros[i] << \" \";\n    }\n    cout << endl;\n    return 0;\n}",
            hint = "Declara un arreglo con int nombre[tamaño] y usa un bucle para imprimir."
        ),

        // INTERMEDIO - C++ (5 ejercicios)
        ExerciseEntity(
            title = "Búsqueda lineal",
            description = "Implementa una búsqueda lineal en un arreglo de enteros.",
            language = "C++",
            level = "Intermedio",
            solutionCode = "#include <iostream>\nusing namespace std;\n\nint busqueda_lineal(int arr[], int n, int objetivo) {\n    for(int i = 0; i < n; i++) {\n        if(arr[i] == objetivo) return i;\n    }\n    return -1;\n}\n\nint main() {\n    int arr[] = {5, 3, 7, 1, 9};\n    cout << busqueda_lineal(arr, 5, 7) << endl;\n    return 0;\n}",
            hint = "Recorre el arreglo comparando cada elemento con el objetivo."
        ),
        ExerciseEntity(
            title = "Invertir arreglo",
            description = "Escribe una función que invierta un arreglo de enteros en su lugar.",
            language = "C++",
            level = "Intermedio",
            solutionCode = "#include <iostream>\nusing namespace std;\n\nvoid invertir(int arr[], int n) {\n    for(int i = 0; i < n/2; i++) {\n        int temp = arr[i];\n        arr[i] = arr[n-1-i];\n        arr[n-1-i] = temp;\n    }\n}\n\nint main() {\n    int arr[] = {1, 2, 3, 4, 5};\n    invertir(arr, 5);\n    for(int i = 0; i < 5; i++) cout << arr[i] << \" \";\n    return 0;\n}",
            hint = "Intercambia elementos desde los extremos hacia el centro usando una variable temporal."
        ),
        ExerciseEntity(
            title = "Clase Rectángulo",
            description = "Crea una clase Rectángulo con atributos ancho y alto, y un método para calcular el área.",
            language = "C++",
            level = "Intermedio",
            solutionCode = "#include <iostream>\nusing namespace std;\n\nclass Rectangulo {\nprivate:\n    int ancho, alto;\npublic:\n    Rectangulo(int a, int h) : ancho(a), alto(h) {}\n    int area() { return ancho * alto; }\n};\n\nint main() {\n    Rectangulo r(5, 3);\n    cout << r.area() << endl;\n    return 0;\n}",
            hint = "Define una clase con atributos privados, constructor y método público."
        ),
        ExerciseEntity(
            title = "Factorial iterativo",
            description = "Calcula el factorial de un número usando un bucle.",
            language = "C++",
            level = "Intermedio",
            solutionCode = "#include <iostream>\nusing namespace std;\n\nlong long factorial(int n) {\n    long long resultado = 1;\n    for(int i = 2; i <= n; i++) {\n        resultado *= i;\n    }\n    return resultado;\n}\n\nint main() {\n    cout << factorial(5) << endl;\n    return 0;\n}",
            hint = "Multiplica consecutivamente desde 1 hasta n usando un bucle."
        ),
        ExerciseEntity(
            title = "Strings y concatenación",
            description = "Crea una función que concatene dos strings y retorne el resultado.",
            language = "C++",
            level = "Intermedio",
            solutionCode = "#include <iostream>\n#include <string>\nusing namespace std;\n\nstring concatenar(string s1, string s2) {\n    return s1 + s2;\n}\n\nint main() {\n    cout << concatenar(\"Hola \", \"Mundo\") << endl;\n    return 0;\n}",
            hint = "Usa el tipo string de la librería estándar y el operador + para concatenar."
        ),

        // AVANZADO - C++ (último ejercicio)
        ExerciseEntity(
            title = "Punteros inteligentes",
            description = "Demuestra el uso de unique_ptr para manejo automático de memoria.",
            language = "C++",
            level = "Avanzado",
            solutionCode = "#include <iostream>\n#include <memory>\nusing namespace std;\n\nclass Objeto {\npublic:\n    Objeto() { cout << \"Objeto creado\" << endl; }\n    ~Objeto() { cout << \"Objeto destruido\" << endl; }\n    void saludar() { cout << \"Hola!\" << endl; }\n};\n\nint main() {\n    unique_ptr<Objeto> ptr = make_unique<Objeto>();\n    ptr->saludar();\n    return 0;\n}",
            hint = "unique_ptr gestiona automáticamente la memoria. Usa make_unique para crear instancias."
        ),
        ExerciseEntity(
            title = "Sobrecarga de operadores",
            description = "Implementa una clase Vector2D con sobrecarga del operador + para sumar vectores.",
            language = "C++",
            level = "Avanzado",
            solutionCode =
                "#include <iostream>\n" +
                        "using namespace std;\n\n" +
                        "class Vector2D {\n" +
                        "private:\n" +
                        "    double x, y;\n" +
                        "public:\n" +
                        "    Vector2D(double x = 0, double y = 0) : x(x), y(y) {}\n\n" +
                        "    Vector2D operator+(const Vector2D& otro) const {\n" +
                        "        return Vector2D(x + otro.x, y + otro.y);\n" +
                        "    }\n\n" +
                        "    void imprimir() const {\n" +
                        "        cout << \"(\" << x << \", \" << y << \")\" << endl;\n" +
                        "    }\n" +
                        "};\n\n" +
                        "int main() {\n" +
                        "    Vector2D v1(1, 2);\n" +
                        "    Vector2D v2(3, 4);\n" +
                        "    Vector2D v3 = v1 + v2;\n" +
                        "    v3.imprimir();\n" +
                        "    return 0;\n" +
                        "}\n",
            hint = "Sobrecarga operator+ retornando un nuevo objeto con la suma de componentes."
        )
    )   // <-- aquí cierras listOf(...) y, por lo tanto, getCppExercises()

    // ========== TIPS DE PROGRAMACIÓN ==========
    fun getTips(): List<TipEntity> = listOf(
        TipEntity(
            category = "General",
            content = "Escribe código limpio y legible. El código se lee más veces de las que se escribe."
        ),
        TipEntity(
            category = "General",
            content = "Usa nombres descriptivos para variables y funciones. Evita abreviaturas confusas."
        ),
        TipEntity(
            category = "Depuración",
            content = "Cuando encuentres un bug, primero reproduce el error de manera consistente antes de intentar arreglarlo."
        ),
        TipEntity(
            category = "Algoritmos",
            content = "Antes de optimizar, asegúrate de que tu código funcione correctamente. La claridad primero, optimización después."
        ),
        TipEntity(
            category = "Python",
            content = "Usa list comprehensions para crear listas de forma concisa: [x*2 for x in range(10)]"
        ),
        TipEntity(
            category = "C++",
            content = "Prefiere referencias constantes (const&) al pasar objetos grandes a funciones para evitar copias innecesarias."
        ),
        TipEntity(
            category = "Práctica",
            content = "Resuelve al menos un ejercicio al día. La consistencia es clave para mejorar tus habilidades."
        ),
        TipEntity(
            category = "General",
            content = "Comenta tu código cuando la intención no sea obvia, pero escribe código que se explique por sí mismo."
        ),
        TipEntity(
            category = "Algoritmos",
            content = "Conoce la complejidad temporal de tus algoritmos. Big O te ayuda a elegir la mejor solución."
        ),
        TipEntity(
            category = "Depuración",
            content = "Usa print statements o un debugger para entender el flujo de tu programa paso a paso."
        )
    )

    // ========== DOCUMENTACIÓN DE AYUDA ==========

    fun getHelpTopics() = listOf(
        // CATEGORÍA: getting_started
        HelpTopicEntity(
            title = "Bienvenido a CodeTrainer",
            category = "getting_started",
            topicOrder = 1, // CAMBIADO: de 'order' a 'topicOrder'
            content = """
            # Bienvenido a CodeTrainer
            
            CodeTrainer es tu compañero perfecto para practicar programación de forma offline. 
            
            ## ¿Qué puedes hacer?
            
            - **Resolver ejercicios** de Python y C++ organizados por dificultad
            - **Seguir tu progreso** con estadísticas detalladas
            - **Mantener una racha diaria** para desarrollar el hábito de programar
            - **Consultar tips y ayuda** cuando lo necesites
            - **Trabajar completamente offline** sin necesidad de conexión a internet
            
            ## Primeros pasos
            
            1. Explora los ejercicios disponibles en la pestaña "Ejercicios"
            2. Filtra por lenguaje (Python/C++) y nivel (Básico/Intermedio/Avanzado)
            3. Selecciona un ejercicio y comienza a programar
            4. Revisa tu progreso en el Dashboard
            
            ¡Feliz coding! 🚀
        """.trimIndent()
        ),
        HelpTopicEntity(
            title = "Introducción a la interfaz",
            category = "getting_started",
            topicOrder = 2, // CAMBIADO
            content = """
            # Introducción a la interfaz
            
            ## Navegación principal
            
            CodeTrainer tiene 4 secciones principales accesibles desde la barra inferior:
            
            ### 📊 Dashboard
            Aquí verás:
            - Un saludo personalizado
            - Tu progreso semanal
            - Ejercicios pendientes
            - Tips aleatorios del día
            
            ### 💻 Ejercicios
            La biblioteca completa de desafíos:
            - Filtra por lenguaje (Python/C++)
            - Filtra por dificultad (Básico/Intermedio/Avanzado)
            - Ve tu progreso en cada ejercicio
            - Accede a resolver ejercicios con un tap
            
            ### 👤 Perfil
            Tus estadísticas personales:
            - Total de ejercicios completados
            - Lenguaje más practicado
            - Racha de días consecutivos
            - Opción para cerrar sesión
            
            ### ⚙️ Ajustes
            Personaliza tu experiencia:
            - Modo oscuro/claro
            - Idioma de la app
            - Recordatorios diarios
            - Gestión de contenido offline
        """.trimIndent()
        ),

        // CATEGORÍA: tutorials
        HelpTopicEntity(
            title = "Cómo resolver tu primer ejercicio",
            category = "tutorials",
            topicOrder = 1, // CAMBIADO
            content = """
            # Cómo resolver tu primer ejercicio
            
            ## Paso a paso
            
            ### 1. Selecciona un ejercicio
            - Ve a la pestaña **Ejercicios**
            - Filtra por "Básico" si eres principiante
            - Elige el lenguaje que prefieras (Python o C++)
            - Tap en cualquier ejercicio para abrirlo
            
            ### 2. Lee el enunciado
            - Comprende qué se te pide
            - Revisa los ejemplos si los hay
            - Identifica las entradas y salidas esperadas
            
            ### 3. Usa el hint si lo necesitas
            - Cada ejercicio tiene una pista
            - Úsala si te quedas atascado
            - No te rindas antes de intentarlo por tu cuenta
            
            ### 4. Escribe tu solución
            - Usa el área de texto proporcionada
            - Escribe tu código paso a paso
            - Prueba diferentes enfoques
            
            ### 5. Envía tu solución
            - Presiona "Enviar solución"
            - La app guardará tu progreso
            - Podrás volver más tarde si lo necesitas
            
            ## Consejos
            
            ✅ Lee el ejercicio completo antes de empezar
            ✅ Descompón el problema en partes pequeñas
            ✅ Escribe pseudo-código si te ayuda
            ✅ Prueba tu código mentalmente con ejemplos
            ✅ No copies y pegues, aprende escribiendo
        """.trimIndent()
        ),
        HelpTopicEntity(
            title = "Sistema de niveles y progreso",
            category = "tutorials",
            topicOrder = 2, // CAMBIADO
            content = """
            # Sistema de niveles y progreso
            
            ## Niveles de dificultad
            
            ### 🟢 Básico
            - Ejercicios fundamentales
            - Sintaxis básica del lenguaje
            - Estructuras de control simples
            - Ideal para principiantes
            
            ### 🟡 Intermedio
            - Algoritmos más complejos
            - Manipulación de estructuras de datos
            - Funciones y modularización
            - Recursión básica
            
            ### 🔴 Avanzado
            - Algoritmos de ordenamiento y búsqueda
            - Estructuras de datos complejas
            - Optimización y eficiencia
            - Programación orientada a objetos
            
            ## Tu progreso
            
            ### Estados de ejercicios
            - **Pendiente**: No has intentado el ejercicio
            - **Completado**: Has enviado una solución
            
            ### Estadísticas
            Tu perfil muestra:
            - Total de ejercicios completados
            - Lenguaje más practicado
            - Racha de días consecutivos practicando
            - Progreso semanal
            
            ## Racha diaria
            
            ¡Mantén tu racha practicando todos los días!
            - Completa al menos 1 ejercicio diario
            - Tu racha aumenta automáticamente
            - Se reinicia si dejas de practicar por un día
        """.trimIndent()
        ),
        HelpTopicEntity(
            title = "Trabajando offline",
            category = "tutorials",
            topicOrder = 3, // CAMBIADO
            content = """
            # Trabajando offline
            
            ## Contenido offline
            
            CodeTrainer está diseñado para funcionar **completamente sin internet** después de la primera configuración.
            
            ### ¿Qué funciona offline?
            
            ✅ Todos los ejercicios (30 disponibles)
            ✅ Resolución de problemas
            ✅ Guardado de progreso
            ✅ Estadísticas y perfil
            ✅ Tips y documentación
            ✅ Todos los ajustes
            
            ### ¿Qué requiere internet?
            
            📡 Solo el login y registro inicial con Firebase
            
            ## Gestión de datos
            
            ### En Ajustes puedes:
            - Ver el estado del contenido offline
            - Reinicializar datos si es necesario
            - Todo se guarda localmente en tu dispositivo
            
            ### Sincronización
            
            Por ahora, CodeTrainer no sincroniza entre dispositivos.
            Tu progreso se mantiene únicamente en tu dispositivo actual.
            
            ## Beneficios del modo offline
            
            ✅ Practica en cualquier lugar
            ✅ Sin consumo de datos móviles
            ✅ Sin distracciones de internet
            ✅ Velocidad máxima
            ✅ Privacidad total
        """.trimIndent()
        ),

        // CATEGORÍA: faq
        HelpTopicEntity(
            title = "Preguntas frecuentes",
            category = "faq",
            topicOrder = 1, // CAMBIADO
            content = """
            # Preguntas frecuentes
            
            ## General
            
            **¿CodeTrainer compila mi código?**
            No. CodeTrainer es una herramienta de práctica. No compila ni ejecuta código real. Su propósito es ayudarte a practicar escribiendo soluciones y desarrollar el hábito de programar.
            
            **¿Puedo usar CodeTrainer sin internet?**
            Sí. Después del login inicial, toda la app funciona completamente offline.
            
            **¿Cuántos ejercicios hay disponibles?**
            Actualmente hay 30 ejercicios: 15 de Python y 15 de C++, divididos equitativamente entre niveles Básico, Intermedio y Avanzado.
            
            ## Cuenta y datos
            
            **¿Cómo recupero mi contraseña?**
            Usa la opción "Olvidé mi contraseña" en la pantalla de login (próximamente).
            
            **¿Mis datos se sincronizan entre dispositivos?**
            No por ahora. Tu progreso se guarda localmente en cada dispositivo.
            
            **¿Puedo cambiar mi email?**
            Actualmente no, pero es una función planificada para futuras versiones.
            
            ## Ejercicios
            
            **¿Cómo sé si mi solución es correcta?**
            CodeTrainer guarda tu solución pero no la evalúa automáticamente. Puedes comparar con la solución sugerida o buscar en recursos externos.
            
            **¿Puedo reiniciar un ejercicio?**
            Sí. Simplemente vuelve a escribir tu solución desde cero.
            
            **¿Se agregarán más lenguajes?**
            Posiblemente en el futuro. Por ahora nos enfocamos en Python y C++.
            
            ## Técnico
            
            **¿En qué plataformas funciona?**
            CodeTrainer es una app Android nativa.
            
            **¿Cuánto espacio ocupa?**
            Menos de 20 MB incluyendo todos los datos offline.
            
            **¿La app recopila mis datos?**
            Solo tu email para autenticación con Firebase. Tu código y progreso se guardan localmente.
        """.trimIndent()
        ),

        // CATEGORÍA: glossary
        HelpTopicEntity(
            title = "Glosario de términos",
            category = "glossary",
            topicOrder = 1, // CAMBIADO
            content = """
            # Glosario de términos
            
            ## Conceptos de programación
            
            **Algoritmo**: Conjunto de pasos ordenados para resolver un problema.
            
            **Variable**: Espacio en memoria que almacena un valor que puede cambiar.
            
            **Función**: Bloque de código reutilizable que realiza una tarea específica.
            
            **Recursión**: Técnica donde una función se llama a sí misma.
            
            **Iteración**: Repetición de un proceso mediante bucles (for, while).
            
            **Array/Arreglo**: Estructura de datos que almacena múltiples valores del mismo tipo.
            
            **Big O**: Notación para expresar la complejidad temporal de un algoritmo.
            
            **Debugging**: Proceso de encontrar y corregir errores en el código.
            
            ## Términos de CodeTrainer
            
            **Ejercicio**: Un desafío de programación para resolver.
            
            **Nivel**: Dificultad del ejercicio (Básico, Intermedio, Avanzado).
            
            **Progreso**: Estado de completitud de los ejercicios.
            
            **Racha**: Días consecutivos completando al menos un ejercicio.
            
            **Hint**: Pista que te ayuda a resolver un ejercicio.
            
            **Offline First**: Diseño que prioriza funcionar sin conexión a internet.
            
            ## Python específico
            
            **List comprehension**: Forma concisa de crear listas: [x*2 for x in range(5)]
            
            **Generator**: Función que usa yield para producir valores bajo demanda.
            
            **Decorator**: Función que modifica el comportamiento de otra función.
            
            **Duck typing**: "Si camina como pato y hace cuac, es un pato" - tipado dinámico.
            
            ## C++ específico
            
            **Pointer**: Variable que almacena una dirección de memoria.
            
            **Reference**: Alias de otra variable.
            
            **Template**: Código genérico que funciona con diferentes tipos.
            
            **STL**: Standard Template Library - biblioteca estándar de C++.
            
            **RAII**: Resource Acquisition Is Initialization - patrón de manejo de recursos.
        """.trimIndent()
        ),
        HelpTopicEntity(
            title = "Atajos y consejos rápidos",
            category = "glossary",
            topicOrder = 2, // CAMBIADO
            content = """
            # Atajos y consejos rápidos
            
            ## Tips de Python
```python
            # Intercambiar valores sin variable temporal
            a, b = b, a
            
            # Múltiples comparaciones
            if 1 < x < 10:
                print("x está entre 1 y 10")
            
            # Valores por defecto en diccionarios
            mi_dict.get('clave', 'valor_por_defecto')
            
            # Enumerar con índice
            for i, valor in enumerate(lista):
                print(f"{i}: {valor}")
            
            # Comprimir listas
            for a, b in zip(lista1, lista2):
                print(a, b)
```
            
            ## Tips de C++
```cpp
            // Auto para inferencia de tipos
            auto x = 5;  // int
            auto y = 3.14;  // double
            
            // Range-based for loop
            for(auto& elemento : vector) {
                // procesar elemento
            }
            
            // Inicialización uniforme
            vector<int> v{1, 2, 3, 4, 5};
            
            // nullptr en lugar de NULL
            int* ptr = nullptr;
            
            // Smart pointers sobre raw pointers
            auto ptr = make_unique<Objeto>();
```
            
            ## Estrategias de resolución
            
            ### 1. Divide y conquista
            Separa el problema en subproblemas más pequeños
            
            ### 2. Fuerza bruta primero
            Haz que funcione, luego optimiza
            
            ### 3. Prueba con ejemplos
            Usa casos de prueba simples primero
            
            ### 4. Edge cases
            Considera: arrays vacíos, números negativos, casos límite
            
            ### 5. Rubber duck debugging
            Explica tu código en voz alta para encontrar errores
            
            ## Patrones comunes
            
            **Two pointers**: Útil para arrays ordenados
            **Sliding window**: Para problemas de subarrays
            **Hash maps**: Para búsquedas rápidas
            **Stack**: Para validación de paréntesis, historial
            **Queue**: Para BFS, procesamiento por niveles
        """.trimIndent()
        )
    )
}

