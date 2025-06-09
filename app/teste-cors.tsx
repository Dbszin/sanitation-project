"use client";
import { useState } from "react";

export default function TesteCors() {
  const [log, setLog] = useState<string>("");

  async function testar() {
    try {
      setLog("Enviando requisição...");
      const res = await fetch("http://localhost:8080/testeCors", {
        method: "GET",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
      });
      setLog(prev => prev + `\nStatus: ${res.status}`);
      const data = await res.json();
      setLog(prev => prev + `\nResposta: ${JSON.stringify(data)}`);
    } catch (err: any) {
      setLog(prev => prev + `\nErro no fetch: ${err.message}`);
    }
  }

  return (
    <div style={{ padding: 20 }}>
      <h1>Teste de CORS</h1>
      <button onClick={testar}>Testar CORS</button>
      <pre style={{ marginTop: 20, padding: 10, background: "#f5f5f5" }}>{log}</pre>
    </div>
  );
} 