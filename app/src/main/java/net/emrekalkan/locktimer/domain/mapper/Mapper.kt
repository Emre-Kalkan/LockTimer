package net.emrekalkan.locktimer.domain.mapper

interface Mapper<Input, Output> {
    suspend operator fun invoke(input: Input): Output
}