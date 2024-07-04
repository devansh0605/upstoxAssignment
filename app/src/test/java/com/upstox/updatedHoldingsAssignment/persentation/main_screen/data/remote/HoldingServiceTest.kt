package com.upstox.updatedHoldingsAssignment.persentation.main_screen.data.remote

import com.upstox.updatedHoldingsAssignment.data.remote.HoldingService
import com.upstox.updatedHoldingsAssignment.domain.model.Holding
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HoldingServiceTest {
    private lateinit var mockWebServer: MockWebServer
    lateinit var holdingService: HoldingService

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        holdingService = Retrofit
            .Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HoldingService::class.java)
    }

    @Test
    fun `Get all holdings test 1`() {
        val mockResponse = MockResponse()
        mockResponse.setBody("{\"data\":{\"userHolding\":[]}}")
        mockWebServer.enqueue(mockResponse)

        val response = runBlocking {
            holdingService.getAllHoldings()
        }

        mockWebServer.takeRequest()

        assertEquals(true, response.body()?.data?.userHolding?.isEmpty())
    }

    @Test
    fun `Get all holdings test 2`() {
        val mockResponse = MockResponse()
        mockResponse.setBody(
            "{\n" +
                    "  \"data\": {\n" +
                    "    \"userHolding\": [\n" +
                    "      {\n" +
                    "        \"symbol\": \"MAHABANK\",\n" +
                    "        \"quantity\": 990,\n" +
                    "        \"ltp\": 38.05,\n" +
                    "        \"avgPrice\": 35,\n" +
                    "        \"close\": 40\n" +
                    "      }\n" +
                    "    ]\n" +
                    "  }\n" +
                    "}"
        )
        mockWebServer.enqueue(mockResponse)

        val response = runBlocking {
            holdingService.getAllHoldings()
        }

        mockWebServer.takeRequest()

        assertEquals(true, response.body()?.data?.userHolding is List<Holding>)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}