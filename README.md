# Gemini App

This is an Android application demonstrating the capabilities of Google's Gemini AI models using the **Firebase Vertex AI SDK**. The app showcases various generative AI features within a modern Android architecture.

## Features

The application includes the following demonstrations:

*   **Text Summary**: Summarize long blocks of text using Gemini.
*   **Conversational Chat**: A chat interface to have back-and-forth conversations with the AI.
*   **Image Description**: Upload or select an image to get a text description of its contents.
*   **Image Generation**: Generate images based on text prompts (if supported by the configured backend).

## Tech Stack

*   **Language**: Kotlin
*   **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose) - Modern toolkit for building native UI.
*   **Architecture**: MVVM (Model-View-ViewModel)
*   **Dependency Injection**: [Koin](https://insert-koin.io/) - Lightweight dependency injection framework for Kotlin.
*   **AI Integration**: [Firebase AI / Vertex AI for Firebase](https://firebase.google.com/docs/vertex-ai) - SDK to access Gemini models.
*   **Navigation**: Jetpack Navigation Compose.
*   **Concurrency**: Kotlin Coroutines & Flow.

## Setup & Configuration

1.  **Clone the repository**:
    ```bash
    git clone https://github.com/your-username/FirstGeminiApp.git
    ```

2.  **Firebase Setup**:
    *   Create a project in the [Firebase Console](https://console.firebase.google.com/).
    *   Enable **Vertex AI for Firebase** in your project.
    *   Download the `google-services.json` file and place it in the `app/` directory.

3.  **Build and Run**:
    *   Open the project in **Android Studio**.
    *   Sync Gradle files.
    *   Run the app on an emulator or physical device.

## Requirements

*   Android Studio Ladybug or newer (recommended).
*   Android SDK 24 or higher.

## License

This project is open source and available under the [MIT License](LICENSE).
