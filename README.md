# Subscriber Prototip

Java Spring Boot ile yazılmış küçük boyutlu bir web uygulaması prototipi. Id, name ve msisdn üye değişkenlerine sahip Subscriber verileri JPA kullanılarak H2 in-memory veri tabanında ve spring starter cache üzerinde tutuluyor. Belirli aralıklarla (cron job) bu veriler *data.json* dosyasına yazılıyor. REST servisler üzerinden veriler sunuluyor ve değiştirilebiliyor. Uygulamaya yapılan tüm HTTP istekleri *app.log* dosyasına loglanıyor. Dependency yönetimi için Maven kullanılıyor.

Konfigürasyon
-------------

- src/main/resources altındaki *app.conf* dosyasındaki **data_file_path** değeri değiştirilerek verilerin tutulduğu data.json dosyasının yeri veya ismi değiştirilebilir.
- src/main/resources altındaki *app.properties* dosyasındaki **cronExpression** değeri değiştirilerek memory'de bulunan verilerin data.json dosyasına yazılması için çalışan scheduler metodunun zaman aralığı değiştirilebilir.

Çalıştırma
------------

1. Projenin base dizininde `$ ./mvnw clean package` komutu çalıştırılarak , veya
2. target dizini içerisindeki *prototip_executable.jar* dosyasını çalıştırarak projeyi başlatabilirsiniz
Spring web uygulaması localhost üzerinde 8080 portunda çalışıyor olacaktır.


Kullanım
----------
Web uygulaması REST servisleri sunarak çalışmaktadır. 

---

Yeni bir subscriber kaydetmek için:

`POST`  - `{host}/subscriber`

---

Kayıtlı tüm subscriber verilerini elde etmek için 

`GET`  - `{host}/getAllSubscribers`

---

Belirli bir subscriber verisine id ile erişmek için

`GET`  - `{host}/getAllSubscribers/getSubscriberById/{id}`

---

Kayıtlı bir subscriber verisini değiştirmek için

`PUT`  - `{host}/subscriber/{id}`

---

Kayıtlı bir subscriber verisini silmek için

`DELETE`  - `{host}/subscriber/{id}`

---

Daha ayrıntılı kullanım bilgileri için Postman koleksiyonumu kullanabilirsiniz:

[Subscriber Prototip Collection](https://www.getpostman.com/collections/8f2301b01106f82c42ab "Subscriber Prototip Collection")
