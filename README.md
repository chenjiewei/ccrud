# ccrud
封装了sp，file,sql三种方式，主要是方便调用，目前不支持集合类的map
使用分三步：
第一步：
//  初始化数据，建议用applicationContext
1、CCRUD.init(context);
第二步：
//  建造SP存储框架
2、CCRUD.buildStorage().setStorageType(StorageType.SHAREPREFERENCE).setFileName("aaa").complete();
// 构造SQL 存储框架
3、CCRUD.buildStorage().setStorageType(StorageType.SQL).setFileName("aaa").setVersion(10).complete();
// 建造File 存储框架
4、CCRUD.buildStorage().setFileName("aaa.txt").setVersion(11).setFilePath(this.getFilesDir().getAbsolutePath()).setStorageType(StorageType.MFILE).complete();
说明：
1、setStorageType();设置存储的类型，分三种：SP ，SQL ,  File
2、构造完一定要调用complete()方法来确认。

第三步（示例）：
1、CCRUD.getStorage(StorageType.SQL).save("9660", list);
2、TextDemo textDemo = CCRUD.getStorage(StorageType.SHAREPREFERENCE).get("543", null, TextDemo.class);
说明：如下所示，更换一下存储方式，给到不同的存储器，并进行操作。


