package Lesson45.practice45.array_list;

import java.util.Arrays;
import java.util.Iterator;

public class MyArrayList<E> implements IList<E> {
    //fields
    private Object[] elements;//массив элементов списка
    private int size;//размер массива


    //constructor
    public MyArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal capacity = " + initialCapacity);
        }
        elements = new Object[initialCapacity];
    }

    public MyArrayList() {
        this(10);
    }

    // O(1) - одна операция
    @Override
    public int size() {
        return size;
    }

    // O(n)
    @Override
    //Метод использует цикл for, чтобы пройти по всем элементам массива elements.
    //Для каждого элемента массива устанавливается значение null, что означает, что ссылка на этот объект больше не существует.
    //Затем размер списка (size) устанавливается в 0, так как все элементы были удалены, и список становится пустым.
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;// Устанавливаем каждый элемент массива в null.
        }
        size = 0;// Устанавливаем размер списка в 0, так как все элементы были удалены.
    }

    //O(n)
    @Override
    //Метод ensureCapacity() проверяет, достиг ли список предела по емкости, и, если необходимо, увеличивает емкость списка. Это делается, чтобы обеспечить достаточное место для добавления нового элемента.
    //После этого новый элемент добавляется в массив elements по индексу size. Затем размер списка (size) увеличивается на 1. Обратите внимание, что это происходит после добавления элемента, поэтому элемент помещается в "текущую" позицию size, а затем размер списка увеличивается.
    //Метод возвращает true, чтобы указать, что элемент был успешно добавлен.
    //Таким образом, метод add(E element) добавляет элемент в конец списка, обновляя размер списка и увеличивая емкость при необходимости.
    public boolean add(E element) {
        ensureCapacity();// Проверяем и, если необходимо, увеличиваем емкость списка.
        elements[size++] = element;// Добавляем элемент в конец списка и увеличиваем размер на 1.
//        size++;       //можно было так -  size++
        return true;// Возвращаем true, чтобы указать, что элемент был успешно добавлен.
    }
// O()

    @Override
    public boolean add(int index, E element) {
        if (index == size) {// Если индекс равен текущему размеру, то добавляем элемент в конец списка.
            add(element);// Вызываем метод add(element) для добавления элемента в конец списка.
            return true; // Возвращаем true, чтобы указать, что элемент был успешно добавлен.
        }
        checkIndex(index); // Проверяем, что индекс находится в допустимых границах.
        ensureCapacity(); // Проверяем и, если необходимо, увеличиваем емкость списка.

        // Используем метод System.arraycopy для эффективной вставки элемента по индексу.
        // element - элемент для вставки, index - индекс, elements - массив элементов списка,
        // index + 1 - начальный индекс для вставки, size++ - index - количество элементов для копирования.
        System.arraycopy(element, index, elements, index + 1, size++ - index);
        elements[index] = element; // Устанавливаем элемент по указанному индексу.
        return true; // Возвращаем true, чтобы указать, что элемент был успешно добавлен.

    }

    @Override
    public E get(int index) {
        checkIndex(index);// Проверяем, что индекс находится в допустимых границах.

        return (E) elements[index];// Возвращаем элемент по указанному индексу, приводя его к типу E.
    }

    //O(n)
    @Override
    public int indexOf(Object o) {
        if (o != null) {// Проверяем, если элемент не null.
            for (int i = 0; i < size; i++) {
                if (o.equals(elements[i])) { // Сравниваем элемент `o` с элементом в списке.
                    return i; // Если элемент найден, возвращаем его индекс.
                }
            }
        } else {// Если элемент `o` равен null.
            for (int i = 0; i < size; i++) {
                if (o == elements[i]) {// Сравниваем элемент `o` с элементом в списке (по ссылке).
                    return i;// Если элемент найден, возвращаем его индекс.
                }
            }
        }return -1;// Если элемент `o` не найден, возвращаем -1.
    }


    //TODO
//реализовать самостоятельно ,запустить циклы справа налево(от конца массива к его началу)
    @Override
    public int lastIndexOf(Object o) {
        if (o != null) {
            for (int i = size; i > 0; i--) {
                if (o.equals(elements[i])) {
                    return i;
                }
            }
        }else {
            if (o == null) {
                for (int i = size; i > 0; i++) {
                    if (o.equals(elements[i])) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }



    @Override
    public E remove(int index) {
        //TODO реализовать самостоятельно по аналогии с add(int index, E element)
        checkIndex(index); // Проверяем, что индекс находится в допустимых границах.

        E removedElement = (E) elements[index]; // Сохраняем удаляемый элемент.

        // Смещаем элементы справа от удаляемого элемента влево для замены удаляемого элемента.
        // После этого уменьшаем размер списка на 1.
        System.arraycopy(elements, index + 1, elements, index, size - index - 1);

        elements[--size] = null; // Устанавливаем последний элемент в null и уменьшаем размер списка.

        return removedElement; // Возвращаем удаленный элемент.
    }



    @Override
    public E set(int index, E element) {
        checkIndex(index); // Проверяем, что индекс находится в допустимых границах.

        E replacedElement = (E) elements[index]; // Сохраняем элемент, который будет заменен новым.

        elements[index] = element; // Устанавливаем новое значение элемента по указанному индексу.

        return replacedElement; // Возвращаем старое значение элемента (которое было заменено новым).
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }





//----------------------------------------------------------------------------------------------------------------------
//Метод сначала проверяет, если текущий размер списка (size) равен емкости массива elements. Если это так, то это означает, что массив заполнен, и его емкость необходимо увеличить.
//Далее, если текущий размер (size) равен максимальному значению Integer.MAX_VALUE, метод генерирует исключение OutOfMemoryError, так как емкость не может быть увеличена дальше.
//Затем метод вычисляет новую емкость, увеличив текущую на половину (elements.length + elements.length / 2). Это увеличение обычно происходит для уменьшения частоты увеличения емкости и оптимизации.
//Если новая емкость отрицательна (что может произойти, если elements.length очень большое), то она устанавливается в максимальное значение Integer.MAX_VALUE.
//Затем создается новый массив с увеличенной емкостью с использованием Arrays.copyOf, и элементы из старого массива копируются в новый.
//После выполнения метода, массив elements будет иметь большую емкость, и в нем будет достаточно места для добавления новых элементов без нехватки памяти.
    private void ensureCapacity() {
        if (size == elements.length) {// Если текущий размер списка равен емкости массива.
            if (size == Integer.MAX_VALUE) {// Проверяем, если размер достиг максимально возможного значения.
                throw new OutOfMemoryError();
            }//проверка на выход за MAX_VALUE

            int newCapacity = elements.length + elements.length / 2;// Вычисляем новую емкость массива, увеличивая ее на половину.
            if (newCapacity < 0) {
                newCapacity = Integer.MAX_VALUE;// Если новая емкость отрицательная, устанавливаем ее в максимальное значение.

            }
            elements = Arrays.copyOf(elements, newCapacity);// Создаем новый массив с увеличенной емкостью и копируем в него элементы.
        }
    }
//-----------------------------------------------------------------------------------------------------------------------
//Метод принимает индекс в качестве аргумента.
//Затем он выполняет проверку, находится ли индекс в допустимых границах, сравнивая его с нулем (для отрицательных индексов) и текущим размером списка (size) (для индексов, больших или равных размеру).
//Если индекс не соответствует допустимым границам, метод генерирует исключение IndexOutOfBoundsException, которое указывает на то, что индекс находится вне допустимых границ списка.
    private void checkIndex(int index) {
        if (index < 0 || index >= size) { // Проверяем, если индекс меньше 0 или больше или равен текущему размеру списка.
            throw new IndexOutOfBoundsException(index); // Генерируем исключение IndexOutOfBoundsException.
        }
    }

}
