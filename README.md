# Java 16 MapMulti Benchmark

- Related StackOverflow question: https://stackoverflow.com/q/64132803/3764965
- Early access JavaDoc of `Stream::mapMulti(BiConsumer)`: https://download.java.net/java/early_access/jdk16/docs/api/java.base/java/util/stream/Stream.html#mapMulti(java.util.function.BiConsumer
- Related OpenJDK issues: [JDK-8238286](https://bugs.openjdk.java.net/browse/JDK-8238286), [JDK-8248166](https://bugs.openjdk.java.net/browse/JDK-8248166)

## Benchmarks

**`Stream::filter(Predicate).map(Function)` vs `Stream::mapMulti(BiConsumer)`**
 - Input size: 10'000
 - The methods generate a list with filtered each 3rd value and mapped an increment by 1
 - Source: `com.nichar.java16.benchmark.MapMulti_FilterMap`
 - Possible results:
   ```
   Benchmark                                   Mode  Cnt    Score  Error  Units
   MultiMap_FilterMap.filterMap                avgt   25   7.973 ± 0.378  ns/op
   MultiMap_FilterMap.mapMulti                 avgt   25   7.765 ± 0.633  ns/op 
   ```

**`Stream::flatMap(Function)` vs `Stream::mapMulti(BiConsumer)`**
 - Input size: 10'000
 - The methods generate and flatten a list of size of the same number of elements as the input number itself
 - Source: `com.nichar.java16.benchmark.MapMulti_FilterMap`
 - Possible results:
   ```
   Benchmark                                   Mode  Cnt   Score   Error  Units
   MultiMap_FlatMap.flatMap                    avgt   25  73.852 ± 3.433  ns/op
   MultiMap_FlatMap.mapMulti                   avgt   25  17.495 ± 0.476  ns/op
   ```

**`Stream::flatMap(Function)` with `Optional::stream()` vs `Stream::mapMulti(BiConsumer)`**
 - Input size: 10'000
 - The methods generate a list with filtered each 3rd value and mapped an increment by 1
 - Source: `com.nichar.java16.benchmark.MapMulti_FilterMap`
 - Possible results:
   ```
   Benchmark                                   Mode  Cnt   Score   Error  Units
   MapMulti_FlatMap_Optional.flatMap           avgt   25  20.186 ± 1.305  ns/op
   MapMulti_FlatMap_Optional.mapMulti          avgt   25  10.498 ± 0.403  ns/op
   ```

## How to run

### Prerequisites

 - **Needed to be done**: 
   - Installed Java 16, at least version `jdk+16-14`
 - **Included in the repository**: 
   - JMH dependencies ready in the `lib` folder: `jmh-core-1.23.jar`, `jmh-generator-annprocess-1.23.jar`, `jopt-simple-4.6.jar`, `commons-math3-3.2.jar`
   - `MANIFEST.MF` file pointing the classpath to the libraries

### Windows
1. Navigate to the root folder 
2. Compile: `"C:\Program Files\Java\jdk-16\bin\javac.exe" -cp ./src/main/java ./src/main/java/com/nichar/java16/*.java ./src/main/java/com/nichar/java16/benchmark/*.java -classpath ./lib/* -d ./out/`
3. Pack the slim JAR file: `"C:\Program Files\Java\jdk-16\bin\jar.exe" cvfm MapMultiBenchmark.jar ./src/main/resources/META-INF/MANIFEST.MF -C ./out/ .`
4. Run the JAR file: `"C:\Program Files\Java\jdk-16\bin\java.exe" -jar MapMultiBenchmark.jar`

Note the path to Java 16 might be different on your machine. 

### Linux
 TO BE DONE