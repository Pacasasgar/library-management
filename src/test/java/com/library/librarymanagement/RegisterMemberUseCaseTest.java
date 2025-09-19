package com.library.librarymanagement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RegisterMemberUseCaseTest {

    @Test
    void registerNewMember_successfully() {
        // 1. Arrange (Preparación)
        // Creamos el caso de uso que vamos a probar
        var registerMemberUseCase = new RegisterMemberUseCase();

        // Datos de entrada para nuestro caso de uso
        String name = "John Doe";
        String email = "john.doe@example.com";

        // 2. Act (Ejecución)
        // Ejecutamos el caso de uso con los datos de entrada
        Member newMember = registerMemberUseCase.execute(name, email);

        // 3. Assert (Verificación)
        // Verificamos que el resultado es el esperado
        assertNotNull(newMember, "El socio no debería ser nulo.");
        assertNotNull(newMember.getMemberId(), "El ID del socio no debería ser nulo tras el registro.");
        assertEquals(name, newMember.getName(), "El nombre del socio no coincide.");
        assertEquals(email, newMember.getEmail(), "El email del socio no coincide.");
    }
}