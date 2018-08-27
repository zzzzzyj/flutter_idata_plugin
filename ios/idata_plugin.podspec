#
# To learn more about a Podspec see http://guides.cocoapods.org/syntax/podspec.html
#
Pod::Spec.new do |s|
  s.name             = 'idata_plugin'
  s.version          = '0.0.1'
  s.summary          = 'A Flutter plugin for iData 95W(A03_A3Q_V0.29) Devices'
  s.description      = <<-DESC
A Flutter plugin for iData 95W(A03_A3Q_V0.29) Devices
                       DESC
  s.homepage         = 'http://example.com'
  s.license          = { :file => '../LICENSE' }
  s.author           = { 'Your Company' => 'email@example.com' }
  s.source           = { :path => '.' }
  s.source_files = 'Classes/**/*'
  s.public_header_files = 'Classes/**/*.h'
  s.dependency 'Flutter'
  
  s.ios.deployment_target = '8.0'
end

