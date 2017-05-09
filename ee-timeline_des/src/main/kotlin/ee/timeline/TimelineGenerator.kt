package ee.timeline
import ee.design.gen.kt.DesignKotlinGenerator
import ee.lang.integ.dPath
import ee.lang.integ.eePath

fun main(args: Array<String>) {
    val generator = DesignKotlinGenerator(Timeline)
    generator.generate(dPath)
}
