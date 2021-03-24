package br.com.zup.pix.persist

import io.micronaut.test.extensions.junit5.annotation.MicronautTest

@MicronautTest
class PixKeyPersistTest {

//    @Inject
//    lateinit var keyRepository: KeyRepository
//
//    @Inject
//    lateinit var createKeyService: CreateKeyService
//
//    @Test
//    fun `tests whether CreateKey correctly converts to PixKey`() {
//        val createKey = CreateKey(
//            clientId = UUID.randomUUID().toString(),
//            type = ReceiverKeyType.CPF,
//            value = "44444444444",
//            accountType = ReceiverAccountType.CONTA_CORRENTE
//        )
//
//        val pixKey = createKey.toModel()
//
//        assertAll({
//            assertEquals(pixKey.clientId.toString(), createKey.clientId)
//            assertEquals(pixKey.keyType, KeyType.CPF)
//            assertEquals(pixKey.keyValue, "444.444.444-44")
//            assertEquals(pixKey.accountType, AccountType.CONTA_CORRENTE)
//        })
//    }
//
//    @Test
//    fun `tests whether CreateKeyService can create valid account`() {
//        val createKey = CreateKey(
//            clientId = "c56dfef4-7901-44fb-84e2-a2cefb157890",
//            type = ReceiverKeyType.CPF,
//            value = "02467781054",
//            accountType = ReceiverAccountType.CONTA_CORRENTE
//        )
//
//        assertAll({
//            assertNotNull(createKeyService.persistKey(createKey))
//            assertTrue(keyRepository.existsByKeyValue(createKey.value!!))
//
//            val findByKeyValue = keyRepository.findByKeyValue(createKey.value!!)
//            assertFalse(findByKeyValue.isEmpty)
//
//            val pixKey = findByKeyValue.get()
//
//            assertEquals(pixKey.clientId.toString(), createKey.clientId)
//            assertEquals(pixKey.keyType, KeyType.CPF)
//            assertEquals(pixKey.keyValue, createKey.value)
//            assertEquals(pixKey.accountType, AccountType.CONTA_CORRENTE)
//        })
//    }
//
//    @Test
//    fun `tests whether CreateKeyService can't create invalid account`() {
//        val createKey = CreateKey(
//            clientId = UUID.randomUUID().toString(),
//            type = ReceiverKeyType.CPF,
//            value = "44444444444",
//            accountType = ReceiverAccountType.CONTA_CORRENTE
//        )
//
//        assertThrows(NotFoundException::class.java) {
//            createKeyService.persistKey(createKey)
//        }
//    }
}