import React from "react";
import { FaFacebook, FaTwitter, FaInstagram, FaLinkedin } from "react-icons/fa";
import { FiSend } from "react-icons/fi";

const Footer = () => {
  return (
    <footer className="footer-custom">
      <div className="container">
        <div className="row">
          {/* Logo và thông tin liên hệ */}
          <div className="col-md-3">
            <h2 className="hotel-logo">StaySavvy</h2>
            <p className="contact-number">+12 345-678-9999</p>
            <span className="footer-span">
              <p>Info.staysavvy@gmail.com</p>
              <p>
                856 Cordia Extension Apt. 356, Lake Deangeloburgh, South Africa
              </p>
            </span>
          </div>

          {/* Blog */}
          <div className="col-md-2">
            <h5>Meet StaySavvy</h5>
            <ul className="footer-list">
              <span className="footer-span">
                <li>FAQs</li>
                <li>Gallery</li>
                <li>Menu</li>
                <li>Restaurant</li>
              </span>
            </ul>
          </div>

          {/* Links */}
          <div className="col-md-2">
            <h5>Links</h5>
            <ul className="footer-list">
              <span className="footer-span">
                <li>Home</li>
                <li>About Us</li>
                <li>Our Room</li>
                <li>Career</li>
                <li>Contact us</li>
              </span>
            </ul>
          </div>

          {/* Subscribe Form */}
          <div className="col-md-4">
            <h5>Subscribe Newsletter</h5>
            <span className="footer-span">
              <p>
                Subscribe our newsletter to get notification about new updates.
              </p>
            </span>

            <div className="subscribe-form">
              <input type="email" placeholder="Enter your email..." />
              <button type="submit">
                <FiSend />
              </button>
            </div>
          </div>
        </div>

        {/* Copyright */}
        <div className="footer-bottom">
          <span className="footer-span">
            <p>Copyright ©2024 All rights reserved</p>
          </span>
          <div className="social-icons">
            <FaFacebook />
            <FaTwitter />
            <FaInstagram />
            <FaLinkedin />
          </div>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
