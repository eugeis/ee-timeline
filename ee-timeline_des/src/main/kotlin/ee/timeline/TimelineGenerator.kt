package ee.timeline

import ee.design.gen.kt.DesignKotlinGenerator
import ee.lang.integ.dPath

fun main(args: Array<String>) {
    val generator = DesignKotlinGenerator(Timeline)
    generator.generate(dPath)
}
