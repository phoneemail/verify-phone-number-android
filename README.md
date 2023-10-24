# Phone.email API Integration in Android

This is a sample Android project demonstrating how to integrate the Phone.email API for phone number verification and OTP authentication in your Android application. For detailed information, you can refer to the blog post [here](https://www.phone.email/blog/phone-number-verification-otp-in-android).

## Getting Started

Follow these steps to integrate the Phone.email API into your Android application.

### Prerequisites

Before you begin, make sure you have the following prerequisites:

- Android Studio installed on your development machine.
- A Phone.email API key. You can obtain one by signing up on [Phone.email](https://play.google.com/store/apps/details?id=email.phone.app) app.
- Phone Email provides a straightforward way to send OTP SMS through their REST API. Here's an example of how to do it in Android using the Retrofit library:

   ```
    @Headers("Content-Type: application/json")
    @POST("sendmail")
    fun sendOtp(@Body otpApiRequest: OtpApiRequest): Call<OtpApiResponse>
   ```

### Resources
- [Phone.email API Documentation](https://documenter.getpostman.com/view/25221564/2s8Z73zWyj#6d56b1f7-f624-45ef-ab22-bbba81bab55a)

### Acknowledgments

[Phone.email](https://www.phone.email/) for providing the Phone.email API.
If you encounter any issues or have questions, please feel free to open an issue in this repository.

Happy coding!

