# ArrayList
It is a project that exemplifies the use of ArrayList methods in Java.

## ArrayList Methodları
#### 1.add(E e)
  Belirtilen öğeyi bu ArrayListin sonuna ekler, boolean değer döndürür.  
#### 2. add(int index, E element)	
  Belirtilen öğeyi bu listede belirtilen konuma ekler.
#### 3 contains(Object o)
ArrayList belirtilen öğeyi içeriyorsa true döndürür.
#### 4. Collections.sort ve ArrayList’de Iterating
Collections sınıfında bir sort() yöntemi bulunmaktadrır. Bu sınıf, java.util paketinin bir parçasıdır. Bu yöntem, bir ArrayList'i sıralamak için kullanılabilir. Aşağıdaki örnekte, String türü listesini alfabetik olarak sıralanmıştır [3].
	Arraylist elemanlarını görüntülemenin doğru yolu, gelişmiş bir for döngüsü kullanmaktır.
#### 5. get(int index)
ArrayList’de belirtilen konumdaki öğeyi döndürür. 
#### 6. indexOf(Object o)  & lastIndexOf(Object o)
indexOf listede belirtilen öğenin ilk dizinini veya bu liste öğeyi içermiyorsa -1'i döndürür.
lastIndexOf listede belirtilen öğenin son dizinini veya bu liste öğeyi içermiyorsa -1'i döndürür. 
#### 7. set(int index, E element)
ArrayList’de belirtilen konumdaki öğeyi belirtilen öğeyle değiştirir. 
#### 8. subList(int fromIndex, int toIndex)
Belirtilen fromIndex (dahil) ve toIndex, arasındaki bu listenin bölümünün bir görünümünü döndürür. 
#### 9. remove(int index) & remove(Object o)
remove(int index): Bu listede belirtilen konumdaki öğeyi kaldırır.
remove(Object o): Varsa, belirtilen öğenin ilk oluşumunu bu listeden kaldırır. 
#### 10. size()
Listedeki eleman sayısını döndürür. 
#### 11. clone()
ArrayList örneğinin bir kopyasını döndürür. 
#### 12. addAll(Collection<? extends E> c) & removeAll(Collection<?> c)
addAll(Collection<?> c): Belirtilen koleksiyondaki tüm öğeleri bu listenin sonuna ekler, boolean değer döndürür [1].
removeAll(Collection<?> c): Belirtilen koleksiyondaki tüm öğeleri bu listeden siler, boolean döndürür [1].
#### 13. clear()
ArrayList’deki tüm elemanları kaldırır.
#### 14. isEmpty()
ArrayList’de hiç eleman bulunmuyorsa true değeri döndürür.
