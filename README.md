# 🤖 fAIz - AI-Powered RAG Chatbot

An intelligent **Retrieval-Augmented Generation (RAG)** chatbot that allows users to upload PDF documents and ask natural language questions based on their content. The application retrieves the most relevant document chunks using vector embeddings and generates context-aware responses using an LLM.

---

## ✨ Features

* 📄 Upload one or multiple PDF documents
* 🔍 Automatic text extraction and chunking
* 🧠 Semantic search using vector embeddings
* 💬 Context-aware question answering
* ⚡ Fast document retrieval
* 📚 Source-based responses
* 🗑️ Delete uploaded documents
* 🔄 Automatic indexing of newly uploaded files
* 🌐 REST APIs for document management and chat

---

## 🏗️ System Architecture

```text
                +----------------+
                |    User        |
                +--------+-------+
                         |
                         v
                Upload PDF / Ask Question
                         |
                         v
                Spring Boot Backend
                         |
        +----------------+----------------+
        |                                 |
        |                                 |
 PDF Processing                    Chat Request
        |                                 |
        v                                 |
 Text Extraction                          |
        |                                 |
        v                                 |
 Chunking                                 |
        |                                 |
        v                                 |
 Generate Embeddings                      |
        |                                 |
        v                                 |
 Store Vectors                            |
        |                                 |
        +------------+--------------------+
                     |
             Similarity Search
                     |
             Relevant Chunks
                     |
                     v
              Prompt + Context
                     |
                     v
                 LLM Response
                     |
                     v
                   User
```

---

# 🛠️ Tech Stack

### Backend

* Java
* Spring Boot
* Spring Web
* Spring AI
* Maven

### AI & NLP

* Retrieval-Augmented Generation (RAG)
* Text Embeddings
* Semantic Search
* Large Language Models (LLMs)

### PDF Processing

* Apache PDFBox

### Storage

* Local File Storage
* In-Memory Vector Store

---

# 📂 Project Structure

```text
src
├── controller
│   ├── ChatController
│   └── DocumentController
│
├── service
│   ├── ChatService
│   ├── PdfService
│   └── EmbeddingService
│
├── model
│
├── config
│
└── resources
```

---

# ⚙️ How It Works

### 1. Upload PDFs

Users upload one or more PDF files.

↓

### 2. Text Extraction

The application extracts text using **Apache PDFBox**.

↓

### 3. Chunking

Large documents are divided into smaller chunks.

↓

### 4. Embedding Generation

Each chunk is converted into a vector embedding.

↓

### 5. Vector Storage

Embeddings are stored in an in-memory vector store.

↓

### 6. User Query

The user's question is converted into an embedding.

↓

### 7. Similarity Search

The application retrieves the most relevant document chunks.

↓

### 8. Response Generation

The retrieved context is sent to the LLM, which generates an accurate answer grounded in the uploaded documents.

---

# 🚀 Getting Started

## Clone the Repository

```bash
git clone https://github.com/Mohammad-Faiz-Shaikh/fAIz.git

cd fAIz
```

---

## Configure Environment

Create an `.env` file or update your `application.properties`.

Example:

```properties
spring.ai.openai.api-key=YOUR_API_KEY
```

If you're using another provider (Gemini, Ollama, etc.), configure the corresponding API keys and endpoints.

---

## Run the Application

```bash
mvn clean install

mvn spring-boot:run
```

---

# 📡 API Endpoints

## Upload Document

```http
POST /upload
```

Upload one or more PDF files.

---

## Ask Question

```http
POST /chat
```

Example:

```json
{
  "question": "What is Retrieval-Augmented Generation?"
}
```

---

## List Uploaded Documents

```http
GET /documents
```

---

## Delete Document

```http
DELETE /documents/{filename}
```

---

# 📖 Example Workflow

1. Upload **Operating Systems.pdf**
2. Upload **Computer Networks.pdf**
3. Ask:

```
Explain Deadlock.
```

The application:

* Finds the most relevant chunks
* Sends them as context to the LLM
* Generates an answer grounded in the uploaded documents

---

# 💡 Future Improvements

* FAISS or ChromaDB integration
* Hybrid Search (Keyword + Semantic)
* Conversation Memory
* Streaming Responses
* OCR support for scanned PDFs
* Authentication & User Management
* Docker deployment
* Multi-user document collections

---

# 🎯 Learning Objectives

This project demonstrates:

* Retrieval-Augmented Generation (RAG)
* Document preprocessing
* Text chunking strategies
* Embedding generation
* Semantic similarity search
* Spring AI integration
* REST API development
* PDF processing with Apache PDFBox

---

# 🤝 Contributing

Contributions, feature requests, and suggestions are welcome.

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Open a Pull Request

---

# 📜 License

This project is licensed under the MIT License.

---

# 👨‍💻 Author

**Mohammad Faiz Shaikh**

* GitHub: https://github.com/Mohammad-Faiz-Shaikh
* LinkedIn: https://www.linkedin.com/in/mohammad-faiz-shaikh

If you found this project useful, consider giving it a ⭐ on GitHub!
