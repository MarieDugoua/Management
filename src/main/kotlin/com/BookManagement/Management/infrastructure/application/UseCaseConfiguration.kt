package com.BookManagement.Management.infrastructure.application

import com.BookManagement.Management.domain.usecase.BookUseCase
import com.BookManagement.Management.infrastructure.driven.adapter.BookDAO
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UseCasesConfiguration {
    @Bean
    fun bookUseCase(bookDAO: BookDAO): BookUseCase {
        return BookUseCase(bookDAO)
    }
}