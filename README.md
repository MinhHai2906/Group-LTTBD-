# AppBanNuoc - Ứng Dụng Bán Nước

Ứng dụng Android hiện đại nhằm hỗ trợ khách hàng mua nước trực tuyến một cách nhanh chóng và tiện lợi. Ứng dụng sở hữu giao diện thân thiện, dễ sử dụng, giúp người dùng dễ dàng lựa chọn sản phẩm, quản lý đơn hàng và thực hiện thanh toán. Sử dụng Jetpack Compose kết hợp quản lý tài khoản trên Firebase và có thêm OpenStreet Map

## Thành viên và đóng góp 

![Thành viên](person.jpg)
![Đóng góp](donggop.jpg)

## App đặt nước online

![Trang chủ](Trangchu.jpg) ![Giỏ hàng](Giohang.jpg) ![Thanh toán](Thanhtoan.jpg)

![ZaloPay](Thanhtoanzalopay1.jpg) ![Thành công](ThanhtoanTC.jpg) ![Trang cá nhân](Trangcanhan.jpg)
---

## Tính Năng Chính

### Tìm Kiếm Sản Phẩm
- Danh sách sản phẩm nước đầy đủ với hình ảnh, giá cả.
- Tìm kiếm nhanh theo tên sản phẩm.

### Quản Lý Giỏ Hàng
- Thêm/xóa sản phẩm vào giỏ hàng
- Chỉnh sửa số lượng sản phẩm
- Tính tổng giá tự động

### Đặt Hàng & Thanh Toán
- Tạo đơn hàng từ giỏ hàng
- Chọn địa chỉ giao hàng bằng OpenStreet Map
- Chọn phương thức thanh toán trực tiếp hoặc bằng Zalopay
- Lịch sử đơn hàng

### Quản Lý Tài Khoản
- Đăng ký/Đăng nhập
- Xem và chỉnh sửa thông tin cá nhân
- Đổi mật khẩu
- Xác minh tài khoản
- Xóa tài khoản

### Giao Diện Thân Thiện
- Giao diện đẹp mắt, dễ sử dụng.
- Hỗ trợ Dark Mode
- Responsive design
- Tương thích tất cả các kích thước màn hình

---

## Tech Stack

**Language:** Kotlin + Java  
**UI:** Jetpack Compose (Material 3) + XML Layout  
**Architecture:** MVVM (ViewModel + LiveData/Coroutines)  

**Database:** Firebase Firestore  
**Networking:** OkHttp3 + Retrofit2 + Gson  
**Authentication:** Firebase Auth + Google Sign-In  

**Maps:** OSMDroid (OpenStreetMap)  
**Image Loading:** Coil  
**Async:** Coroutines  
**Build Tool:** Gradle (Kotlin DSL)  

**Min SDK:** 24  
**Target SDK:** 36  
**Java Version:** 11
---

## Cấu Trúc Dự Án

```
DuAnCuoiKi/
├── .gradle/
│   ├── file-system.probe
│   ├── 9.1.0/
│   │   ├── gc.properties
│   │   ├── checksums/
│   │   ├── executionHistory/
│   │   ├── expanded/
│   │   ├── fileChanges/
│   │   ├── fileHashes/
│   │   └── vcsMetadata/
│   ├── buildOutputCleanup/
│   │   └── cache.properties
│   └── vcs-1/
│       └── gc.properties
├── .idea/
│   ├── .gitignore
│   ├── .name
│   ├── AndroidProjectSystem.xml
│   ├── compiler.xml
│   ├── deploymentTargetSelector.xml
│   ├── deviceManager.xml
│   ├── gradle.xml
│   ├── migrations.xml
│   ├── misc.xml
│   ├── runConfigurations.xml
│   ├── vcs.xml
│   ├── workspace.xml
│   ├── caches/
│   │   └── deviceStreaming.xml
│   └── inspectionProfiles/
│       └── Project_Default.xml
├── .kotlin/
│   ├── errors/
│   └── sessions/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── AndroidManifest.xml
│   │   │   ├── java/
│   │   │   │   └── com/example/wateronl/
│   │   │   │       ├── Api/
│   │   │   │       │   ├── CreateOrder.java
│   │   │   │       │   └── HttpProvider.java
│   │   │   │       ├── Constant/
│   │   │   │       │   └── AppInfo.java
│   │   │   │       ├── Helper/
│   │   │   │       │   ├── HMac/
│   │   │   │       │   └── Helpers.java
│   │   │   │       └── ui/
│   │   │   │           ├── theme/
│   │   │   │           │   ├── Color.kt
│   │   │   │           │   ├── Theme.kt
│   │   │   │           │   ├── Type.kt
│   │   │   │           │   ├── AppColors.kt
│   │   │   │           │   ├── AvatarList.kt
│   │   │   │           │   ├── BottomBar.kt
│   │   │   │           │   └── [Các file UI component khác]
│   │   │   │           ├── ChiTietDonHang.kt
│   │   │   │           ├── DangKy.kt
│   │   │   │           ├── DonHang.kt
│   │   │   │           ├── GioHang.kt
│   │   │   │           ├── GioHangData.kt
│   │   │   │           ├── LichSuDonHang.kt
│   │   │   │           ├── LoginViewmodel.kt
│   │   │   │           ├── MainActivity.kt
│   │   │   │           ├── ManHinhCho.kt
│   │   │   │           ├── MapScreen.kt
│   │   │   │           ├── Navitem.kt
│   │   │   │           ├── Profile.kt
│   │   │   │           ├── ProfileViewModel.kt
│   │   │   │           ├── SharedPreferencesManager.kt
│   │   │   │           ├── ThanhToan.kt
│   │   │   │           ├── ThongBao.kt
│   │   │   │           ├── Trang.kt
│   │   │   │           ├── TrangDangNhap.kt
│   │   │   │           └── WaterOnApplication.kt
│   │   │   └── res/
│   │   ├── androidTest/
│   │   │   └── java/
│   │   └── test/
│   │       └── java/
│   ├── libs/
│   │   └── zpdk-release-v3.1.aar
│   ├── build/
│   │   ├── generated/
│   │   ├── gmpAppId/
│   │   ├── intermediates/
│   │   ├── kotlin/
│   │   ├── outputs/
│   │   └── tmp/
│   ├── .gitignore
│   ├── build.gradle.kts
│   ├── google-services.json
│   └── proguard-rules.pro
├── build/
│   └── reports/
│       └── problems/
│           └── problems-report.html
├── gradle/
│   ├── libs.versions.toml
│   └── wrapper/
│       └── gradle-wrapper.properties
├── .gitignore
├── build.gradle.kts
├── gradle.properties
├── gradlew
├── gradlew.bat
├── local.properties
└── settings.gradle.kts
```

## Hướng Dẫn Cài Đặt và Chạy Dự Án

### 1 Chuẩn Bị Môi Trường

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

### 2 Cài Đặt Dependencies

#### Sync Gradle:
```bash
./gradlew sync
```

Hoặc dùng Android Studio: **File → Sync Now**

### 3 Build & Run

#### Chạy trên Emulator/Device:
```bash
# Build debug APK
./gradlew build

# Cài đặt và chạy ứng dụng
./gradlew installDebug

# Hoặc chạy trực tiếp (cần device/emulator)
./gradlew runDebug
```

**Hoặc dùng Android Studio:**
- Click **Run** (Shift + F10)
- Chọn device/emulator

### 4 Build Production

#### Tạo Release APK:
```bash
# Build release APK
./gradlew assembleRelease

# Output: app/build/outputs/apk/release/app-release.apk
```

#### Tạo Release AAB (Google Play):
```bash
./gradlew bundleRelease

# Output: app/build/outputs/bundle/release/app-release.aab
```

### 5 Cấu Hình Quan Trọng

#### Firebase Setup:
- Authentication: Email/Password + Google Sign-In
- Firestore Database: Để lưu dữ liệu người dùng

#### API Keys:
Hiện tại project sử dụng:
- Firebase Auth (có trong google-services.json)
- Google Play Services (phần Google Sign-In)

#### Min/Target SDK:
- **Min SDK:** 24 (Android 7.0+)
- **Target SDK:** 36 (Android 15)

---

## Troubleshooting

### Lỗi Gradle sync:
```bash
./gradlew clean build
```

### Lỗi Firebase:
- Kiểm tra `google-services.json` đúng vị trí
- Build → Clean Project

### Lỗi dependencies:
```bash
./gradlew dependencyInsight --configuration debugRuntimeClasspath --dependency [packageName]
```

### Build không thành công:
1. Xóa folder `.gradle` và `build`:
   ```bash
   ./gradlew clean
   ```
2. Sync lại Gradle
3. Rebuild project

---

---
## Hướng Dẫn Sử Dụng

### Đăng Nhập/Đăng Ký

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
  - Chọn tài khoản Google của bạn
  - Cấp quyền truy cập khi được yêu cầu

### Trang Chủ

Sau khi đăng nhập, bạn sẽ vào **Trang Chủ** với:
- Danh sách sản phẩm nước

### Mua Sắm

#### Duyệt Sản Phẩm:
1. Trang chủ hiển thị danh sách các loại nước uống.
2. Cuộn để xem thêm sản phẩm.
3. Nhấn vào hình ảnh để xem rõ hơn.

#### Xem Chi Tiết Sản Phẩm:
1. Xem hình ảnh, giá cả
2. Lựa chọn:
   - Số lượng (nhấn +/- để điều chỉnh)
3. Nhấn **"Thêm vào giỏ hàng"**
4. Tiếp tục mua nước

### Giỏ Hàng

#### Xem Giỏ Hàng:
1. Nhấn vào biểu tượng **Giỏ Hàng** ở dưới bottombar
2. Xem danh sách sản phẩm đã chọn

#### Quản Lý Giỏ Hàng:
- **Tăng/Giảm Số Lượng:** Nhấn +/- bên cạnh sản phẩm
- **Xóa Sản Phẩm:** Nhấn nút xóa
- **Xem Tổng Tiền:** Hiển thị ở cuối danh sách

### Thanh Toán

#### Bước Đặt Hàng:
1. Ở giỏ hàng, nhấn **"Thanh Toán"**
2. **Chọn Địa Chỉ Giao Hàng:**
   - Nhấn **"Nhập Địa Chỉ"** để chọn trên Map
3. **Chọn Phương Thức Thanh Toán:**
   - Thanh toán bằng Zalopay
   - Thanh Toán Khi Nhận Hàng (COD)
4. **Xem Lại Đơn Hàng:**
   - Danh sách sản phẩm
   - Địa chỉ giao hàng
   - Tổng tiền (bao gồm phí vận chuyển)
5. Nhấn **"Xác Nhận Đặt Hàng"**

#### Các Tài Khoản Để Thử Nghiệm

> **Lưu ý:** Những tài khoản dưới đây chỉ dành cho **test/demo**. Không sử dụng trên môi trường production.
> **Lưa ý** Cần tải Zalopay ảo để không bị thoát đăng nhập của app Zalopay thật dưới đây là link.
> (https://docs.zalopay.vn/docs/developer-tools/test-instructions/test-wallets/)

##### 1. Thẻ Visa.

| Thông Tin | Giá Trị |
|-----------|--------|
| **Số Thẻ** | 4111111111111111 |
| **Tên Chủ Thẻ** | NGUYEN VAN A |
| **Ngày Hết Hạn** | 01/28 | lưu ý ở đây ngày không được thay đổi năm cần phải lớn hơn năm hiện tại
| **Mã CVV** | 123 |

**Hướng dẫn sử dụng:**
1. Chọn phương thức thanh toán: **Thẻ Visa**
2. Nhập các thông tin trên
3. Nhấn **"Thanh Toán"** để hoàn tất

##### 2. Danh Sách Thẻ Nội địa

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
4. Nhập mã OTP luôn là **111111**
5. Nhấn **"Thanh Toán"** để hoàn tất

##### Lưu ý Quan Trọng:
- Những tài khoản này chỉ dùng cho **test & demo** trên môi trường development
- Không sử dụng trên ứng dụng production
- Hệ thống sẽ không trừ tiền khi sử dụng tài khoản test

6. Chờ xác nhận từ hệ thống
   - Nếu thành công → Đơn hàng được lưu
   - Nếu thất bại → Thử lại hoặc chọn phương thức khác

### Đơn Hàng

#### Xem Danh Sách Đơn Hàng:
1. Nhấn vào biểu tượng **"Đơn Hàng"** ở bottombar
2. Xem tất cả đơn hàng của bạn

#### Xem Chi Tiết Đơn Hàng:
1. Nhấn vào một đơn hàng trong danh sách
2. Xem thông tin chi tiết:
   - **Trạng Thái:** Chờ xác nhận 
   - **Danh Sách Sản Phẩm:** Các mặt hàng trong đơn
   - **Địa Chỉ Giao:** Nơi nhận hàng
   - **Ngày Đặt/Giao:** Thời gian dự kiến
   - **Tổng Tiền:** Chi phí đơn hàng

### Quản Lý Tài Khoản

#### Xem & Chỉnh Sửa Hồ Sơ:
1. Nhấn vào **"Hồ Sơ"** ở bottombar
2. Xem thông tin cá nhân:
   - Tên, Email, Số Điện Thoại
   - Ảnh Đại Diện
3. Nhấn **"Chỉnh Sửa"** để thay đổi thông tin
4. Nhấn **"Lưu"** để cập nhật

#### Đổi Mật Khẩu:
1. Ở hồ sơ, nhấn **"Bảo Mật"**
2. Nhấn **"Đổi Mật Khẩu"**
3. Nhập:
   - Mật khẩu hiện tại
   - Mật khẩu mới (tối thiểu 6 ký tự)
   - Xác nhận mật khẩu mới
4. Nhấn **"Cập Nhật"**

#### Xóa tài khoản:
1. Ở cài đặt
2. Nhấn **"Xóa tài khoản"**
3. Tài khoản sẽ được xóa và quay về trang đăng nhập
#### Đăng Xuất:
1. Ở hồ sơ, cuộn xuống
2. Nhấn **"Đăng Xuất"**
3. Xác nhận → Quay về màn hình đăng nhập

---
## API Endpoints

```
GET    /api/products              # Lấy danh sách sản phẩm
GET    /api/products/:id          # Chi tiết sản phẩm
POST   /api/cart                  # Thêm vào giỏ hàng
GET    /api/cart                  # Lấy giỏ hàng
DELETE /api/cart/:id              # Xóa khỏi giỏ hàng
POST   /api/orders                # Tạo đơn hàng
GET    /api/orders                # Lịch sử đơn hàng
GET    /api/orders/:id            # Chi tiết đơn hàng
POST   /api/auth/register         # Đăng ký
POST   /api/auth/login            # Đăng nhập
GET    /api/user                  # Thông tin tài khoản
```

---

## License

Dự án này được phát triển cho mục đích học tập.

---

## Tác Giả

AppBanNuoc Team - Ứng Dụng Bán Nước Trực Tuyến
## Nguyễn Minh Hải
## Nguyễn Hồng Quang

---

## Liên Hệ & Hỗ Trợ

Nếu có bất kỳ vấn đề hoặc câu hỏi, hãy liên hệ nhóm phát triển.

---