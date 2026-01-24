# 💧 Water Online - Ứng Dụng Đặt Nước Online

## 📋 Mục Lục
- [Giới Thiệu](#-giới-thiệu)
- [Cấu Trúc Dự Án](#-cấu-trúc-dự-án)
- [Tech Stack](#-tech-stack)
- [Hướng Dẫn Cài Đặt](#-hướng-dẫn-cài-đặt-và-chạy-dự-án)
- [Hướng Dẫn Sử Dụng](#-hướng-dẫn-sử-dụng)
- [Troubleshooting](#-troubleshooting)

---

## 📱 Giới Thiệu

**Water Online** là ứng dụng mobile cho phép người dùng đặt nước uống trực tuyến. Ứng dụng cung cấp các tính năng:

- ✅ Đăng ký/Đăng nhập bằng Email hoặc Google Account
- ✅ Xem danh sách sản phẩm nước
- ✅ Quản lý giỏ hàng
- ✅ Đặt hàng và thanh toán
- ✅ Theo dõi lịch sử đơn hàng và trạng thái giao hàng
- ✅ Xem vị trí giao hàng trên bản đồ
- ✅ Quản lý thông tin cá nhân và địa chỉ giao hàng
- ✅ Nhận thông báo đơn hàng

---

## 📁 Cấu Trúc Dự Án

```
DuAnCuoiKi/
├── .gradle/
├── .idea/
├── .kotlin/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── AndroidManifest.xml
│   │   │   ├── java/
│   │   │   │   └── com/example/wateronl/
│   │   │   │       ├── Api/
│   │   │   │       ├── Constant/
│   │   │   │       ├── Helper/
│   │   │   │       └── ui/
│   │   │   └── res/
│   │   ├── androidTest/
│   │   └── test/
│   ├── libs/
│   ├── build/
│   ├── build.gradle.kts
│   ├── google-services.json
│   └── proguard-rules.pro
├── build/
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── local.properties
├── gradlew
└── gradlew.bat
```

---

## 🛠️ Tech Stack

| Category | Technology | Version |
|----------|-----------|---------|
| **Language** | Kotlin + Java | - |
| **UI Framework** | Jetpack Compose | Material 3 |
| **Architecture** | MVVM Pattern | - |
| **Async Programming** | Coroutines | 1.7.3 |
| **Networking** | OkHttp3 | 4.12.0 |
| **JSON Parsing** | Gson | 2.10.1 |
| **Database** | Firebase Firestore | - |
| **Authentication** | Firebase Auth + Google Sign-In | 20.7.0 |
| **Maps** | OSMDroid | 6.1.18 |
| **Image Loading** | Coil | 2.6.0 |
| **Build Tool** | Gradle | Kotlin DSL |
| **Min SDK** | 24 (Android 7.0+) | - |
| **Target SDK** | 36 (Android 15) | - |

---

## 🚀 Hướng Dẫn Cài Đặt và Chạy Dự Án

### 1️⃣ Chuẩn Bị Môi Trường

#### Yêu cầu hệ thống:
- **Android Studio:** Giraffe+ (API 36)
- **JDK:** 11+
- **Gradle:** Tự động đi kèm

#### Cấu hình dự án:
1. Clone/Tải dự án về máy
2. Tạo file `local.properties` ở root project:
```properties
sdk.dir=C:\\Users\\YourName\\AppData\\Local\\Android\\Sdk
```

3. Thêm `google-services.json` vào thư mục `app/`:
   - Tải từ Firebase Console
   - Đặt tại: `app/google-services.json`

### 2️⃣ Cài Đặt Dependencies

#### Sync Gradle:
```bash
./gradlew sync
```

Hoặc dùng Android Studio: **File → Sync Now**

### 3️⃣ Build & Run

#### Chạy trên Emulator/Device:
```bash
# Build debug APK
./gradlew build

# Cài đặt và chạy ứng dụng
./gradlew installDebug

# Hoặc chạy trực tiếp
./gradlew runDebug
```

**Hoặc dùng Android Studio:**
- Click **Run** (Shift + F10)
- Chọn device/emulator

### 4️⃣ Build Production

#### Tạo Release APK:
```bash
./gradlew assembleRelease
# Output: app/build/outputs/apk/release/app-release.apk
```

#### Tạo Release AAB (Google Play):
```bash
./gradlew bundleRelease
# Output: app/build/outputs/bundle/release/app-release.aab
```

---

## 📖 Hướng Dẫn Sử Dụng

### 🔐 Đăng Nhập/Đăng Ký

#### Đăng Ký Tài Khoản Mới:
1. Mở ứng dụng, nhấn vào màn hình đăng nhập
2. Nhấn **"Đăng Ký"** để tạo tài khoản mới
3. Nhập thông tin:
   - Email (sử dụng làm tên đăng nhập)
   - Mật khẩu (tối thiểu 6 ký tự)
   - Xác nhận mật khẩu
4. Nhấn **"Đăng Ký"** để hoàn tất
5. Nếu thành công, sẽ quay về màn hình đăng nhập

#### Đăng Nhập:
- **Cách 1:** Nhập email & mật khẩu → Nhấn **"Đăng Nhập"**
- **Cách 2:** Nhấn nút **"Google"** để đăng nhập bằng Google Account

### 🏠 Trang Chủ

Sau khi đăng nhập, bạn sẽ vào **Trang Chủ** với:
- 📍 Địa chỉ giao hàng mặc định
- 🔔 Nút thông báo
- 🔍 Thanh tìm kiếm sản phẩm
- 📦 Danh sách sản phẩm nước

### 🛍️ Mua Sắm

#### Duyệt Sản Phẩm:
1. Trang chủ hiển thị danh sách các loại nước uống
2. Nhấn vào sản phẩm để xem chi tiết

#### Xem Chi Tiết Sản Phẩm:
1. Xem hình ảnh, mô tả, giá cả
2. Lựa chọn số lượng
3. Nhấn **"Thêm vào giỏ hàng"**

### 🛒 Giỏ Hàng

#### Xem Giỏ Hàng:
1. Nhấn vào biểu tượng **Giỏ Hàng** (🛒) ở dưới cùng
2. Xem danh sách sản phẩm đã chọn

#### Quản Lý Giỏ Hàng:
- **Tăng/Giảm Số Lượng:** Nhấn +/- bên cạnh sản phẩm
- **Xóa Sản Phẩm:** Nhấn nút xóa (🗑️)

### 💳 Thanh Toán

#### Bước Đặt Hàng:
1. Ở giỏ hàng, nhấn **"Thanh Toán"**
2. **Chọn Địa Chỉ Giao Hàng:**
   - Địa chỉ mặc định hiển thị sẵn
   - Nhấn **"Đổi Địa Chỉ"** để chọn hoặc thêm mới
3. **Chọn Phương Thức Thanh Toán:**
   - Thẻ Tín Dụng/Ghi Nợ (Visa, Master, JCB)
   - Ví Điện Tử
   - Thanh Toán Khi Nhận Hàng (COD)
4. **Xem Lại Đơn Hàng:**
   - Danh sách sản phẩm
   - Địa chỉ giao hàng
   - Tổng tiền
5. Nhấn **"Xác Nhận Đặt Hàng"**

#### 🧪 Các Tài Khoản Để Thử Nghiệm

> **Lưu ý:** Những tài khoản dưới đây chỉ dành cho **test/demo**. Không sử dụng trên môi trường production.

##### 1. Thẻ Tín Dụng - Visa, Master, JCB

| Thông Tin | Giá Trị |
|-----------|--------|
| **Số Thẻ** | 4111111111111111 |
| **Tên Chủ Thẻ** | NGUYEN VAN A |
| **Ngày Hết Hạn** | 01/25 |
| **Mã CVV** | 123 |

**Hướng dẫn sử dụng:**
1. Chọn phương thức thanh toán: **Thẻ Tín Dụng**
2. Nhập các thông tin trên
3. Nhấn **"Thanh Toán"** để hoàn tất

##### 2. Danh Sách Thẻ ATM (Test với Bank SBI)

| STT | Số Thẻ ATM | Tên Chủ Thẻ | Ngân Hàng |
|-----|-----------|------------|----------|
| 1 | 9704540000000062 | NGUYEN VAN A | SBI |
| 2 | 9704540000000070 | NGUYEN VAN A | SBI |
| 3 | 9704540000000088 | NGUYEN VAN A | SBI |
| 4 | 9704540000000096 | NGUYEN VAN A | SBI |
| 5 | 9704541000000094 | NGUYEN VAN A | SBI |
| 6 | 9704541000000078 | NGUYEN VAN A | SBI |

**Hướng dẫn sử dụng:**
1. Chọn phương thức thanh toán: **Ví Điện Tử** hoặc **Ngân Hàng**
2. Chọn ngân hàng **SBI**
3. Nhập một trong các số thẻ ATM trên
4. Nhập mã OTP nếu cần
5. Nhấn **"Thanh Toán"** để hoàn tất

##### ⚠️ Lưu ý Quan Trọng:
- Những tài khoản này chỉ dùng cho **test & demo** trên môi trường development
- Không sử dụng trên ứng dụng production
- Nếu muốn thanh toán thực, sử dụng thẻ/tài khoản ngân hàng thực của bạn
- Hệ thống sẽ không trừ tiền khi sử dụng tài khoản test

### 📦 Theo Dõi Đơn Hàng

#### Xem Danh Sách Đơn Hàng:
1. Nhấn vào biểu tượng **"Đơn Hàng"** (📦) ở dưới cùng
2. Xem tất cả đơn hàng của bạn

#### Xem Chi Tiết Đơn Hàng:
1. Nhấn vào một đơn hàng trong danh sách
2. Xem thông tin chi tiết:
   - **Trạng Thái:** Chờ xác nhận / Đang giao / Đã giao / Đã hủy
   - **Danh Sách Sản Phẩm**
   - **Địa Chỉ Giao**
   - **Tổng Tiền**

#### Theo Dõi Vị Trí:
1. Ở chi tiết đơn hàng, nhấn **"Xem Bản Đồ"**
2. Bản đồ hiển thị:
   - 📍 Vị trí cửa hàng
   - 📍 Vị trí giao hàng
   - 🚗 Vị trí tài xế (nếu đang giao)

### 👤 Quản Lý Tài Khoản

#### Xem & Chỉnh Sửa Hồ Sơ:
1. Nhấn vào **"Hồ Sơ"** (👤) ở dưới cùng
2. Xem thông tin cá nhân
3. Nhấn **"Chỉnh Sửa"** để thay đổi

#### Quản Lý Địa Chỉ:
1. Ở hồ sơ, nhấn **"Địa Chỉ Giao Hàng"**
2. **Thêm Địa Chỉ Mới / Xóa / Đặt Làm Mặc Định**

#### Đổi Mật Khẩu:
1. Ở hồ sơ, nhấn **"Bảo Mật"**
2. Nhấn **"Đổi Mật Khẩu"**
3. Nhập mật khẩu cũ và mới

#### Đăng Xuất:
1. Ở hồ sơ, cuộn xuống
2. Nhấn **"Đăng Xuất"**

### 🔔 Thông Báo

1. Nhấn vào nút **Thông Báo** (🔔) ở trang chủ
2. Xem các thông báo:
   - 📩 Cập nhật trạng thái đơn hàng
   - 🎁 Khuyến mãi & Ưu đãi
   - ⚠️ Thông báo hệ thống

---

## ⚠️ Troubleshooting

### Lỗi Gradle sync:
```bash
./gradlew clean build
```

### Lỗi Firebase:
- Kiểm tra `google-services.json` đúng vị trí
- Build → Clean Project

### Lỗi Đăng Nhập:
- Kiểm tra kết nối Internet
- Kiểm tra Firebase Console có được cấu hình đúng

### Lỗi Thanh Toán:
- Kiểm tra địa chỉ giao hàng đã chọn
- Thử lại hoặc chọn phương thức thanh toán khác

### Build không thành công:
```bash
./gradlew clean
```

---

## 📧 Liên Hệ & Hỗ Trợ

Nếu có bất kỳ vấn đề hoặc câu hỏi, hãy liên hệ nhóm phát triển.

---

**Last Updated:** January 2026
