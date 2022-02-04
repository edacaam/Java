# Lambda Expression
<br> Lambda expressions Java 8'e dahil edilmiş bir özelliktir. Bir lambda ifadesi, parametreleri alan ve bir değer döndüren kısa bir kod bloğudur. 
<br> Lambda ifadeleri metotlara benzer, ancak bir isme ihtiyaç duymazlar ve doğrudan bir metodun gövdesinde uygulanabilirler .
<br> Lambda ifadeleri temel olarak işlevsel arayüzlerin örneklerini ifade eder (Yalnızca bir soyut yöntemi olan bir arayüze fonksiyonel(işlevsel) arayüz denir. Örneğin java.lang.Runnable bir işlevsel arabirimdir.).
<br> Bir Java lambda ifadesi herhangi bir sınıfa ait olmadan oluşturulabilen bir fonksiyondur. Bir Java lambda ifadesi, bir nesneymiş gibi etrafta dolaştırılabilir ve talep üzerine yürütülebilir.
<br> Lamda ifadelerin koleksiyonlarda sıkça kullanılmaktadır. Koleksiyondan verilerin yinelenmesine, filtrelenmesine ve çıkarılmasına yardımcı olur. 

<br> Örnek 1:
- Listedeki her öğeyi yazdırmak için ArrayList'in forEach() yönteminde bir lamba ifadesi kullanılabilir. Listedeki öğeler için lamda ifadeden yararlanılır.

<br> Örnek 2:
- Bir arabirimin referansını bildirebilir, ancak bir arabirimi somutlaştıramayız. Referansa bir lambda ifadesi atanır. Arayüzün yöntemini çağırmak lambda ifadesini çalıştıracaktır.
- Fonksiyonel bir arayüz olan Message arayüzü tanımlanmıştır. Bu arayüz String değer döndüren bir greeting fonksiyonuna sahiptir. Bu arayüzün referansı message şeklinde bildirilmiş ve lambda ifade tanımlanarak mesaj nesnesine atanmıştır. Arayüz nesnesinin greeting() metodu çağrıldığında lamda ifadesi de çalıştırılmış olur dönen değer konsolda gösterilir.

<br> Örnek 3: 
- Matematiksel ifadeleri gerçekleştirmek üzere fonksiyonel bir arayüz olan MathOperation arayüzü tanımlanmıştır. Bu arayüz operation adında 2 tane integer parametre alan ve integer değer döndüren bir fonksiyona sahiptir.
- MathOperation interfaceinden üretilen addition,subtraction,multiplication ve division nesnelerinin lambda expressionlar sayesinde toplama, çıkarma, çarpma ve bölme işlemleri yapacak şekilde tanımlanmıştır. Bu MathOperation nesnelerinin operation(int,int) metodu çağırıldığında lamda ifadeler çalıştırılacak ve işlemler hesaplanacaktır. Bu işlemleri simüle etmek için static bir metot tanımlanmıştır. Bu metot işlem yapılacak int değerleri ve MathOperation nesnesini almaktadır.Bu fonksiyonda MathOperation arayüzünün operation(int,int) metodu çalıştırılarak işlemlerin hesaplanması sağlanmış ve sonuçlar konsola basılarak simüle edilmiştir.	
