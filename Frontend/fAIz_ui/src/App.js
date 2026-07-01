import { useState, useRef, useEffect, useCallback } from 'react';
import './App.css';

const API = 'http://localhost:8085/api';

function SplashScreen({ onDone }) {
  const [fading, setFading] = useState(false);

  useEffect(() => {
    const t1 = setTimeout(() => setFading(true), 2000);
    const t2 = setTimeout(onDone, 2700);
    return () => { clearTimeout(t1); clearTimeout(t2); };
  }, [onDone]);

  return (
    <div className={`splash${fading ? ' splash-fade' : ''}`}>
      <div className="splash-name">
        <span>f</span><span className="ai">AI</span><span>z</span>
      </div>
      <div className="splash-sub">Your intelligent document assistant</div>
    </div>
  );
}

export default function App() {
  const [ready, setReady] = useState(false);
  const onDone = useCallback(() => setReady(true), []);
  const [messages, setMessages] = useState([]);
  const [input, setInput] = useState('');
  const [loading, setLoading] = useState(false);
  const [uploading, setUploading] = useState(false);
  const [uploadMsg, setUploadMsg] = useState('');
  const bottomRef = useRef(null);
  const fileRef = useRef(null);
  const [fileNames, setFileNames] = useState([]);
  const [fileButton, setFileButton] = useState(false);

  useEffect(() => {
    bottomRef.current?.scrollIntoView({ behavior: 'smooth' });
  }, [messages, loading]);

  const getNames = async () => {
    try {
      const request = await fetch(`${API}/document/get/pdf`);
      const response = await request.json();
      setFileNames(response);
      setFileButton(!fileButton);
    } catch {
      setUploadMsg('✗ Could not fetch file names.');
    }
  };

  const deletePdf = async (file) => {
    try {
      await fetch(`${API}/document/delete/${file}`, { method: 'DELETE' });
      setFileNames(prev => prev.filter(f => f !== file));
      getNames();
      setUploadMsg(`✔️${file} Deleted Succesfully`)
    } catch {
      setUploadMsg('✗ Could not delete file.');
    }
  };

  const sendMessage = async (e) => {
    e.preventDefault();
    if (!input.trim()) return;

    const question = input.trim();
    setInput('');
    setMessages(prev => [...prev, { role: 'user', text: question }]);
    setLoading(true);

    try {
      const res = await fetch(`${API}/chat`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ question }),
      });
      const text = await res.text();
      setMessages(prev => [...prev, { role: 'bot', text }]);
    } catch {
      setMessages(prev => [...prev, { role: 'bot', text: 'Error: could not reach the server.' }]);
    } finally {
      setLoading(false);
    }
  };

  const uploadFile = async (e) => {
    const file = e.target.files[0];
    if (!file) return;
    setUploading(true);
    setUploadMsg('');

    const form = new FormData();
    form.append('file', file);

    try {
      const res = await fetch(`${API}/document/upload`, { method: 'POST', body: form });
      const text = await res.text();
      setUploadMsg(`✓ ${file.name} — ${text}`);
    } catch {
      setUploadMsg('✗ Upload failed.');
    } finally {
      setUploading(false);
      fileRef.current.value = '';
    }
  };

  return (
    <>
      {!ready && <SplashScreen onDone={onDone} />}
      <div className={`app${ready ? ' app-enter' : ' app-hidden'}`}>
        <header className="header">
          <span className="header-icon">🤖</span>
          <h1>NewBot</h1>
          <label className="upload-btn" title="Upload document">
            {uploading ? <span className="spinner" /> : '📎'}
            <input ref={fileRef} type="file" hidden onChange={uploadFile} />
          </label>
          <button onClick={getNames}>{fileButton ? 'Hide Files' : 'Show Files'}</button>
        </header>

        {uploadMsg && <div className="upload-msg">{uploadMsg}</div>}

        <main className="messages">
          {messages.length === 0 && fileButton === false && (
            <div className="empty">Ask me anything, or upload a document first.</div>
          )}
          {messages.map((m, i) => (
            <div key={i} className={`bubble ${m.role}`}>
              <span className="avatar">{m.role === 'user' ? '🧑' : '🤖'}</span>
              <p>{m.text}</p>
            </div>
          ))}
          {loading && (
            <div className="bubble bot">
              <span className="avatar">🤖</span>
              <p className="typing"><span /><span /><span /></p>
            </div>
          )}
          {fileButton && (
            <ul className="file-list">
              {fileNames.map((file, index) => (
                <li key={index}>📄 {file} <button onClick={() => deletePdf(file)}>❌</button></li>
              ))}
            </ul>
          )}
          <div ref={bottomRef} />
        </main>

        <form className="input-bar" onSubmit={sendMessage}>
          <input
            value={input}
            onChange={e => setInput(e.target.value)}
            placeholder="Type a message…"
            disabled={loading}
          />
          <button type="submit" disabled={loading || !input.trim()}>Send</button>
        </form>
      </div>
    </>
  );
}
