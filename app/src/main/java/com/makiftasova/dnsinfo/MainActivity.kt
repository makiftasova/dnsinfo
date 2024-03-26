package com.makiftasova.dnsinfo

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.makiftasova.dnsinfo.ui.theme.DnsInfoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DnsInfoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PrintList("List of DNS servers in use", getDnsServersList())
                }
            }
        }
    }

    private fun getDnsServersList(): List<String> {
        val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val linkProperties =
            connectivityManager.getLinkProperties(connectivityManager.activeNetwork)
                ?: return listOf("No DNS Servers configured!")

        if (linkProperties.dnsServers.size < 1)
            return listOf("No DNS Servers configured!")

        return linkProperties.dnsServers.map { it.hostAddress } as List<String>
    }
}

@Composable
fun PrintList(message: String, items: List<String>, modifier: Modifier = Modifier) {
    var text = "$message:\n"
    items.forEach{text += "$it\n"}
    Text(
        text = text,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun PrintListPreview() {
    DnsInfoTheme {
        PrintList(message = "List of DNS servers in use", items = listOf("1.1.1.1", "1.0.0.1"))
    }
}