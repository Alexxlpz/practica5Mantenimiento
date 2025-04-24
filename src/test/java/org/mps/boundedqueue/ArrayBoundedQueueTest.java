package org.mps.boundedqueue;

import static org.assertj.core.api.Assertions.*;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

//Grupo M: Pablo Gálvez Castillo, Alejandro López Ortega

public class ArrayBoundedQueueTest {
    
    private ArrayBoundedQueue<Integer> cola;
    private int capacity = 5;

    @BeforeEach
    void setup(){
        cola = new ArrayBoundedQueue<>(capacity);
        cola.put(1);
        cola.put(2);
        cola.put(3);
    }

    @Nested
    @DisplayName("pruebas para el contructor de ArrayBoundedQueue")
    public class ConstructorArrayBoundedQueueTest {
        
        @Test
        @DisplayName("Constructor con una capacidad igual o menor que 0 da IllegalArgumentException")
        public void ArrayBoundedQueueConstructorCapacidadErronea(){
            //Arrange
            //Act
            //Assert
            assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> {
                new ArrayBoundedQueue<>(0);
            }).withMessageContaining("ArrayBoundedException: capacity must be positive");
        }

        @Test
        @DisplayName("Constructor con una capacidad mayor que 0, objeto creado correctamente")
        public void ArrayBoundedQueueConstructorCapacidadCorrecta(){
            //Arrange
            ArrayBoundedQueue<Integer> array = new ArrayBoundedQueue<>(10);
            //Act
            //Assert
            assertThat(array).isEmpty();
            assertThat(array.size()).isZero();
            assertThat(array.getFirst()).isZero();
            assertThat(array.getLast()).isZero();
        }
    }
    
        @Nested
        @DisplayName("Pruebas metodo put(T value)")
        public class putTests{
    
            @Test
            @DisplayName("put funciona correctamente")
            public void putFuncionaCorrectamente(){
                //Arrange
                int initialSize = cola.size();
                int initialLast = cola.getLast();
                int element = 9;
                //Act
                cola.put(element);
                //Assert
                assertThat(cola)
                    .isNotEmpty()
                    .contains(element)
                    .containsExactly(1, 2, 3, element);
                assertThat(cola.size()).isEqualTo(initialSize + 1);
                assertThat(cola.getLast()).isEqualTo((initialLast + 1) % capacity);
            }
    
            @Test
            @DisplayName("put(null) lanza excepcion")
            public void putValorNuloLanzaExcepcion(){
                //Arrange
                //Act
                //Assert
                assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(() -> {cola.put(null);});
            }
    
            @Test
            @DisplayName("put lanza excepcion si la cola esta llena")
            public void putColaLlenaLanzaExcepcion(){
                //Arrange
                int rest = capacity - cola.size();
                for(int i = 0; i < rest; i++)
                    cola.put(i + rest);
                assertThatExceptionOfType(FullBoundedQueueException.class)
                    .isThrownBy(() -> {cola.put(capacity + 1);});
            }
        }

    @Nested
    @DisplayName("pruebas para el metodo Get de ArrayBoundedQueue")
    public class GetArrayBoundedQueueTest {
    
        @Test
        @DisplayName("hacemos el get sobre un array vacio, salta EmptyBoundedQueueException")
        public void ArrayBoundedQueueGetConArrayVacio(){
            //Arrange
            ArrayBoundedQueue<Integer> array = new ArrayBoundedQueue<>(10);
            //Act
            //Assert
            assertThatExceptionOfType(EmptyBoundedQueueException.class)
            .isThrownBy(() -> {
                array.get();
            }).withMessageContaining("get: empty bounded queue");
        }

        @Test
        @DisplayName("hacemos el get sobre un array vacio, salta EmptyBoundedQueueException")
        public void ArrayBoundedQueueGetConArrayConValores(){
            //Arrange
            Integer expected = 1;
            //Act
            Integer prevFirst = cola.getFirst();
            Integer prevSize = cola.size();
            //Assert
            assertThat(cola.get())
            .isNotNull()
            .isEqualTo(expected);

            assertThat(cola.getFirst()).isNotEqualTo(prevFirst);
            assertThat(cola.size()).isEqualTo(prevSize-1);
        }
    }

    @Test
    @DisplayName("isFull funciona correctamente")
    public void isFullFuncionaCorrectamente(){
        //Arrange
        boolean initial = cola.isFull();
        int rest = capacity - cola.size();
        for(int i = 0; i < rest; i++)
                cola.put(i + rest);
        //Act
        //Arrange
        assertThat(initial).isFalse();
        assertThat(cola.isFull()).isTrue();
    }

    @Nested
    @DisplayName("pruebas para el metodo isEmpty de ArrayBoundedQueue")
    public class IsEmptyArrayBoundedQueueTest {
    
        @Test
        @DisplayName("hacemos el isEmpty sobre un array vacio, devuelve true")
        public void ArrayBoundedQueueGetConArrayVacio(){
            //Arrange
            ArrayBoundedQueue<Integer> array = new ArrayBoundedQueue<>(10);
            //Act
            //Assert
            assertThat(array.isEmpty()).isTrue();
        }

        @Test
        @DisplayName("hacemos el isEmpty sobre un array vacio, devuelve False")
        public void ArrayBoundedQueueGetConArrayConValores(){
            //Arrange
            //Act
            //Assert
            assertThat(cola.isEmpty()).isFalse();
        }
    }

    @Test
    @DisplayName("size funciona correctamente")
    public void sizeFuncionaCorrectamente(){
        //Arrange
        int initial = cola.size();
        int rest = capacity - cola.size();
        for(int i = 0; i < rest; i++)
                cola.put(i + rest);
        //Act
        //Arrange
        assertThat(initial).isEqualTo(3);
        assertThat(cola.size()).isEqualTo(capacity);
    }

    @Nested
    @DisplayName("pruebas para el metodo getFirst de ArrayBoundedQueue")
    public class getFirstArrayBoundedQueueTest {
    
        @Test
        @DisplayName("hacemos el getFirst sobre un array vacio, te dara un 0")
        public void ArrayBoundedQueueGetConArrayVacio(){
            //Arrange
            ArrayBoundedQueue<Integer> array = new ArrayBoundedQueue<>(10);
            //Act
            //Assert
            assertThat(array.getFirst()).isZero();
        }

        @Test
        @DisplayName("hacemos el getFirst sobre un array lleno, dara 0 al principio y al hacer un get pasa al siguiente")
        public void ArrayBoundedQueueGetConArrayConValores(){
            //Arrange
            //Act
            //Assert
            assertThat(cola.getFirst()).isZero();
            cola.get();
            assertThat(cola.getFirst()).isEqualTo(1);
        }
    }

    @Test
    @DisplayName("getLast funciona correctamente")
    public void getLastFuncionaCorrectamente(){
        //Arrange
        int initial = cola.getLast();
        int rest = capacity - cola.size();
        for(int i = 0; i < rest; i++)
                cola.put(i + rest);
        //Act
        //Arrange
        assertThat(initial).isEqualTo(3);
        assertThat(cola.getLast()).isEqualTo(cola.size() % capacity);
    }


    @Nested
    @DisplayName("Pruebas iterator()")
    public class iteratorTests{
        @Test
        @DisplayName("iterator con cola vacia funciona correctamente")
        public void iteratorVacioFuncionaCorrectamente(){
            //Arrange
            ArrayBoundedQueue<Integer> vacia = new ArrayBoundedQueue<>(5);
            //Act
            Iterator<Integer> iter = vacia.iterator();
            //Assert
            assertThat(iter.hasNext()).isFalse();
            assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() -> {iter.next();});
        }

        @Test
        @DisplayName("iterator con cola con elementos funciona correctamente")
        public void iteratorConElementosFuncionaCorrectamente(){
            //Arrange
            //Act
            Iterator<Integer> iter = cola.iterator();
            //Assert
            assertThat(iter.hasNext()).isTrue();
            assertThat(iter.next()).isEqualTo(1);
            assertThat(iter.hasNext()).isTrue();
            assertThat(iter.next()).isEqualTo(2);
        }
    }


}
